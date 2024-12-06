package com.muhammad.myapplication.forvia.presentation.app_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.muhammad.myapplication.R
import com.muhammad.myapplication.forvia.core.presentation.extensions.toDateOnly
import com.muhammad.myapplication.forvia.core.presentation.extensions.toFormattedRating
import com.muhammad.myapplication.forvia.core.presentation.extensions.toFormattedSize
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.presentation.app_list.previewApp
import com.muhammad.myapplication.forvia.presentation.custom_components.CustomText
import com.muhammad.myapplication.ui.theme.TechChallengeAppTheme

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun InventoryDetailScreen(
    appInventory: AppInventory,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigate: () -> Unit
) {
    var isDownloadBtnClick by remember { mutableStateOf(false) }

    with(sharedTransitionScope) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.detail)) },
                navigationIcon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable {
                                onNavigate.invoke()
                            },
                    )
                },
            )
        }) { padding ->
            LazyColumn(contentPadding = padding) {
                item {
                    GlideImage(
                        model =appInventory.imageUrl,
                        contentDescription = "inventory picture",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.CenterStart,
                        modifier = Modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = "image/${appInventory.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            )
                            .height(346.dp)
                            .fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(16.dp))
                    InventoryBasicInfoItem(
                        name = appInventory.name,
                        updated = appInventory.updated.toDateOnly(),
                        download = appInventory.size.toFormattedSize(),
                        animatedVisibilityScope = animatedVisibilityScope,
                        onDownloadClick = {
                            isDownloadBtnClick =  !isDownloadBtnClick
                        }
                    )
                }
                item {
                    InventoryInfo(appInventory = appInventory)
                }
            }
            if (isDownloadBtnClick) {
                DialogBottomSheet(onDismiss = {
                    isDownloadBtnClick = false
                })
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.InventoryBasicInfoItem(
    name: String,
    updated: String,
    download: String,
    onDownloadClick: () -> Unit = {},
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.align(CenterVertically)) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "text/${name}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier   .clip(
                    RoundedCornerShape(8.dp))
                    .clickable {
                        onDownloadClick()
                    }
                 .padding(end = 12.dp),
            ) {

                Image(painter = painterResource(id = R.drawable.download),
                    modifier = Modifier.size(20.dp, 20.dp),
                    contentDescription = null)
                Text(
                    text = download, modifier = Modifier.padding(
                        start = 8.dp, top = 0.dp, end = 12.dp, bottom = 0.dp
                    ), style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}

@Composable
fun InventoryInfo(appInventory: AppInventory) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        Title(title = stringResource(R.string.app_inventory_info))
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
        ) {
            InfoCard(
                primaryText = appInventory.storeName,
                secondaryText = stringResource(R.string.store),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
            InfoCard(
                primaryText = appInventory.updated.toDateOnly(),
                secondaryText = stringResource(R.string.last_updated),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
            InfoCard(
                primaryText = appInventory.rating.toFormattedRating(),
                secondaryText = stringResource(R.string.rating),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomText(text = secondaryText, maxLines = 2, fontSize = 11, lineHeight = 12)
            Text(
                text = primaryText,
                fontSize = 10.sp,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(4.dp))
        }

    }

}


@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewLightDark
@Composable
private fun AppDetailScreenPrview() {
    TechChallengeAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                InventoryDetailScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    appInventory = previewApp,
                    onNavigate = {}
                   )
            }
        }
    }
}

