package com.muhammad.myapplication.forvia.data.mapper

import com.muhammad.myapplication.forvia.data.local.AppInventoryEntity
import com.muhammad.myapplication.forvia.data.remote.response.AppInventoryDto
import com.muhammad.myapplication.forvia.data.remote.response.AppInventoryResponseDto
import com.muhammad.myapplication.forvia.domain.model.AppInventory



//  This transformation is necessary because DTOs are often used for network responses,
// while domain models are used within the app's business logic.
// This separation helps maintain a clean architecture and makes the code more maintainable.

//Network responses are often nested and complex. The mapper helps to extract the relevant data

fun AppInventoryDto.toDomainModel(): AppInventory {
    return AppInventory(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        rating = this.rating,
        size = this.size,
        updated = this.updated,
        storeName = this.storeName,
        versionName = this.versionName
    )
}

fun AppInventoryEntity.toDomainInventory(): AppInventory {
    return AppInventory(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        rating = this.rating,
        size = this.size,
        updated = this.updated,
        storeName = this.storeName,
        versionName = this.versionName
    )
}

fun AppInventoryDto.toEntity(): AppInventoryEntity {
    return AppInventoryEntity(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        rating = this.rating,
        size = this.size,
        updated = this.updated,
        storeName = this.storeName,
        versionName = this.versionName
    )
}

fun List<AppInventoryDto>.toDomainModelList(): List<AppInventory> {
    return this.map { it.toDomainModel() }
}

fun AppInventoryResponseDto.getAppInventoryList(): List<AppInventoryDto> {
    return this.responses.listApps.datasets.all.data.list
}

fun AppInventoryResponseDto.toEntityList(): List<AppInventoryEntity> {
    return this.getAppInventoryList().map { it.toEntity() }
}
