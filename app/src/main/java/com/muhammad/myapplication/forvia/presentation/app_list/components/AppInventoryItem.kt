package com.muhammad.myapplication.forvia.presentation.app_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.presentation.custom_components.CustomText


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun AppInventoryItem(
    appInventory: AppInventory,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onInventoryItemClick: (AppInventory) -> Unit,
) {
    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = { onInventoryItemClick(appInventory) }),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    GlideImage(
                        model =appInventory.imageUrl,
                        contentDescription = "profile picture",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.CenterStart,
                        modifier = Modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = "image/${appInventory.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            )
                            .size(80.dp,80.dp)
                            .clip(RoundedCornerShape(16.dp)),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        CustomText(
                            text = appInventory.name,
                            maxLines = 2,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "text/${appInventory.name}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomText(
                            text = buildString {
                                append(appInventory.storeName)
                                append(" | ")
                                append(appInventory.storeName)
                            },
                            )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                tint = Color.Red,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp, 16.dp),
                            )
                            CustomText(
                                fontSize = 10,
                                text = appInventory.rating.toString(), modifier = Modifier.padding(
                                    start = 8.dp, top = 0.dp, end = 12.dp, bottom = 0.dp
                                ), textStyle = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun ForivaItemPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {

            AppInventoryItem(
                appInventory = AppInventory(
                    id = "1",
                    name = "Muhammad Ammar",
                    imageUrl = "https://example.com/graphic.png",
                    rating = 3.2,
                    size = 1234,
                    updated = "12-01-2024",
                    storeName = "X",
                    graphic = "https://example.com/graphic.png",
                    versionName = "1.6v",
                ),
                sharedTransitionScope = this@SharedTransitionLayout,
                animatedVisibilityScope = this,
                onInventoryItemClick = {}
            )
        }
    }
}