package com.example.exoplayerpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.exoplayerpractice.player.PlayerScreen
import com.example.exoplayerpractice.select.SelectScreen
import com.example.exoplayerpractice.ui.theme.ExoPlayerPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            ExoPlayerPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "select",
                    ) {
                        composable(route = "select") {
                            SelectScreen(
                                onFileSelected = { uri ->
                                    navController.navigate("player?uri=$uri")
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        composable(
                            route = "player?uri={uri}",
                            arguments = listOf(navArgument("uri") { NavType.StringType })
                        ) { navBackStackEntry ->
                            val uri = requireNotNull(navBackStackEntry.arguments?.getString("uri"))
                            PlayerScreen(
                                mediaUri = uri,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
