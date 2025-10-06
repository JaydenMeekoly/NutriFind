package com.example.nutrifind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.nutrifind.data.FavoritesManager
import com.example.nutrifind.data.model.Recipe
import com.example.nutrifind.ui.favorites.FavoritesScreen
import com.example.nutrifind.ui.home.HomeScreen
import com.example.nutrifind.ui.recipes.RecipeDetailScreen
import com.example.nutrifind.ui.recipes.RecipeScreen
import com.example.nutrifind.ui.theme.NutriFindTheme
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    currentScreen: Screen,
    onScreenChange: (Screen) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(Screen.Home, Screen.Search, Screen.Favorites).forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = { onScreenChange(screen) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize FavoritesManager
        FavoritesManager.initialize(applicationContext)
        
        setContent {
            NutriFindTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
                var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
                
                MainScreen(
                    currentScreen = currentScreen,
                    onScreenChange = { screen ->
                        currentScreen = screen
                    }
                ) {
                    when (currentScreen) {
                        Screen.Home -> {
                            HomeScreen(
                                onGetStarted = { currentScreen = Screen.Search }
                            )
                        }
                        Screen.Search -> {
                            if (selectedRecipe != null) {
                                RecipeDetailScreen(
                                    recipe = selectedRecipe!!,
                                    onBackClick = { selectedRecipe = null }
                                )
                            } else {
                                RecipeScreen(
                                    onRecipeClick = { recipe -> 
                                        selectedRecipe = recipe 
                                    }
                                )
                            }
                        }
                        Screen.Favorites -> {
                            FavoritesScreen(
                                onRecipeClick = { recipe -> 
                                    selectedRecipe = recipe
                                    currentScreen = Screen.Search
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}