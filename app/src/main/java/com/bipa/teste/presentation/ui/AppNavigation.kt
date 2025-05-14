package com.bipa.teste.presentation.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bipa.teste.util.NetworkUtils



@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                navController = navController,
                onTimeout = {
                    navController.navigate("offline") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("main") {
            if (NetworkUtils.isConnected(context)) {
                //NodeScreen
            } else {
                navController.navigate("offline") {
                    popUpTo("main") { inclusive = true }
                }
            }

        }

        composable("offline") {
            OfflineScreen()
        }
    }
}
