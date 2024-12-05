package com.muhammad.myapplication.forvia.core.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.muhammad.myapplication.forvia.domain.model.AppInventory
import com.muhammad.myapplication.forvia.presentation.app_detail.InventoryDetailScreen
import com.muhammad.myapplication.forvia.presentation.app_list.AppInventoryScreen
import com.muhammad.myapplication.forvia.presentation.app_list.viewmodel.AppInventoryViewModel
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MyNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Home) {
            composable<Home> {
                val viewModel = hiltViewModel<AppInventoryViewModel>()
                val state = viewModel.state.collectAsStateWithLifecycle().value
                val event = viewModel.events

                AppInventoryScreen(
                    state = state,
                    events = event,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                ) {
                    navController.navigate(Detail(it))
                }
            }

            composable<Detail>(
                typeMap = mapOf(
                    typeOf<AppInventory>() to CustomNavType.AppType,
                )
            ) {
                val arguments = it.toRoute<Detail>()
//                val detail =  Detail = it.toRoute()
                InventoryDetailScreen(
                    appInventory = arguments.appInventory,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                ) {
                    navController.navigateUp()
                }
            }
        }
    }
}
