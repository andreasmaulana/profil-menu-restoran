package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.ui.MainViewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Observe absolute Dark Theme value from SharedPreferences
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()

            MyApplicationTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Set up screen navigation with slide & fade animations (Vibe Coding Item [2])
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = { slideInHorizontally { it } + fadeIn() },
                        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
                        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
                        popExitTransition = { slideOutHorizontally { it } + fadeOut() }
                    ) {
                        // Layar 1: HomeScreen (Layar Utama)
                        composable("home") {
                            HomeScreen(
                                viewModel = viewModel,
                                onNavigateToMenu = { navController.navigate("menu") },
                                onNavigateToProfile = { navController.navigate("profile") }
                            )
                        }

                        // Layar 2: MenuScreen (Daftar Menu)
                        composable("menu") {
                            MenuScreen(
                                viewModel = viewModel,
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToDetail = { menuId -> navController.navigate("detail/$menuId") }
                            )
                        }

                        // Layar 3: DetailMenuScreen (Detail Lengkap Menu)
                        composable(
                            route = "detail/{menuId}",
                            arguments = listOf(navArgument("menuId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val menuId = backStackEntry.arguments?.getString("menuId") ?: ""
                            DetailMenuScreen(
                                viewModel = viewModel,
                                menuId = menuId,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        // Layar 4: ProfileScreen (Profil Restoran)
                        composable("profile") {
                            ProfileScreen(
                                viewModel = viewModel,
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToEdit = { navController.navigate("edit_profile") }
                            )
                        }

                        // Layar 5: EditProfileScreen (Edit Profil Restoran)
                        composable("edit_profile") {
                            EditProfileScreen(
                                viewModel = viewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
