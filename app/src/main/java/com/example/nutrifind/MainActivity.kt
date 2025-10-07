package com.example.nutrifind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nutrifind.data.FavoritesManager
import com.example.nutrifind.data.SettingsManager
import com.example.nutrifind.data.model.Recipe
import com.example.nutrifind.navigation.Screen
import com.example.nutrifind.ui.favorites.FavoritesScreen
import com.example.nutrifind.ui.home.HomeScreen
import com.example.nutrifind.ui.recipes.RecipeDetailScreen
import com.example.nutrifind.ui.recipes.RecipeScreen
import com.example.nutrifind.ui.settings.SettingsScreen
import com.example.nutrifind.ui.theme.NutriFindTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize managers
        FavoritesManager.initialize(applicationContext)
        SettingsManager.initialize(applicationContext)
        
        setContent {
            NutriFindApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriFindApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route
    
    // Show bottom navigation for main screens
    val showBottomBar = listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Favorites.route,
        Screen.Settings.route
    ).contains(currentRoute)
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    val items = listOf(
                        Screen.Home,
                        Screen.Search,
                        Screen.Favorites,
                        Screen.Settings
                    )
                    
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { 
                                Icon(
                                    imageVector = screen.icon ?: Icons.Default.Settings,
                                    contentDescription = screen.title
                                ) 
                            },
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onGetStarted = { navController.navigate(Screen.Search.route) }
                )
            }
            
            composable(Screen.Search.route) {
                RecipeScreen(
                    onRecipeClick = { recipe ->
                        val recipeJson = Json.encodeToString(recipe)
                        navController.navigate("${Screen.RecipeDetail.route}/$recipeJson")
                    }
                )
            }
            
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    onRecipeClick = { recipe ->
                        val recipeJson = Json.encodeToString(recipe)
                        navController.navigate("${Screen.RecipeDetail.route}/$recipeJson")
                    }
                )
            }
            
            composable(
                route = "${Screen.RecipeDetail.route}/{recipeJson}",
                arguments = listOf(navArgument("recipeJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val recipeJson = backStackEntry.arguments?.getString("recipeJson")
                val recipe = remember(recipeJson) {
                    Json.decodeFromString<Recipe>(recipeJson ?: "")
                }
                
                RecipeDetailScreen(
                    recipe = recipe,
                    onBackClick = { navController.navigateUp() }
                )
            }
            
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}