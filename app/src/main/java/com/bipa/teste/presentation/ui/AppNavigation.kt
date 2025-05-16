package com.bipa.teste.presentation.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bipa.teste.presentation.viewmodel.NodeViewModel
import com.bipa.teste.util.NetworkUtils



@Composable
fun AppNavigation(viewModel: NodeViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                onTimeout = {
                    navController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                }

            )
        }

        composable("main") {
            if (NetworkUtils.isConnected(context)) {
                NodeListScreen(viewModel)
            } else {
                navController.navigate("offline") {
                    popUpTo("main") { inclusive = true }
                }
            }

        }

        composable("offline") {
            OfflineScreen(
                onRetry = {
                    if (NetworkUtils.isConnected(context)) {
                        navController.navigate("main") {
                            popUpTo("offline") { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}
