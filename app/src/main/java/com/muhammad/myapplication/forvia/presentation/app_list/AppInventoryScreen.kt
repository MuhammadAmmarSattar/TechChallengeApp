package com.muhammad.myapplication.forvia.presentation.app_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.muhammad.myapplication.forvia.presentation.app_list.state.AppListState
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.presentation.app_list.components.AppInventoryItem
import com.muhammad.myapplication.forvia.presentation.custom_components.Loader
import com.muhammad.myapplication.ui.theme.TechChallengeAppTheme


/**
 * I use sharedTransitionScope and animatedVisibilityScope to
 * animate the transition between screens.
 * for good user experience!!
 **/

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppInventoryScreen(
    modifier: Modifier = Modifier,
    state: AppListState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onInventoryClick: (AppInventory) -> Unit
) {
    Scaffold(
        topBar = {
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(state.appInventory) { inventory ->
                    AppInventoryItem(
                        appInventory = inventory,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    ) {
                        onInventoryClick(inventory)
                    }
                }
            }
            Loader(isLoading = state.isLoading)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewLightDark
@Composable
private fun AppInventoryScreenPreview() {
    TechChallengeAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                AppInventoryScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    onInventoryClick = {},
                    state = AppListState(appInventory = (1..50).map {
                        previewApp.copy(id = it.toString())
                    })
                )
            }
        }
    }
}

val previewApp = AppInventory(
    id = "com.example.app",
    name = "Example App",
    imageUrl = "https://example.com/image.png",
    rating = 4.5,
    size = 10240000,
    updated = "2024-12-03",
    storeName = "Example Store",
    graphic = "https://example.com/graphic.png",
    versionName = "1.0.0"
)

