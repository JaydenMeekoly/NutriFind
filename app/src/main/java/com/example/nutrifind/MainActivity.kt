package com.example.nutrifind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.nutrifind.data.model.Recipe
import com.example.nutrifind.ui.home.HomeScreen
import com.example.nutrifind.ui.recipes.RecipeDetailScreen
import com.example.nutrifind.ui.recipes.RecipeScreen
import com.example.nutrifind.ui.theme.NutriFindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriFindTheme {
                var showHomeScreen by remember { mutableStateOf(true) }
                var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when {
                        showHomeScreen -> {
                            HomeScreen(onGetStarted = { showHomeScreen = false })
                        }
                        selectedRecipe != null -> {
                            RecipeDetailScreen(
                                recipe = selectedRecipe!!,
                                onBackClick = { selectedRecipe = null }
                            )
                        }
                        else -> {
                            RecipeScreen(
                                onRecipeClick = { recipe -> selectedRecipe = recipe }
                            )
                        }
                    }
                }
            }
        }
    }
}