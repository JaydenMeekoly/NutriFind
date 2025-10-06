package com.example.nutrifind.ui.recipes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.BuildConfig
import com.example.nutrifind.data.api.ApiClient
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.util.Locale

private const val TAG = "RecipeViewModel"
private const val DEFAULT_PAGE_SIZE = 20

class RecipeViewModel : ViewModel() {
    private val _uiState = mutableStateOf<RecipeUiState>(RecipeUiState.Loading)
    val uiState: State<RecipeUiState> = _uiState
    
    private val apiKey = BuildConfig.SPOONACULAR_API_KEY
    
    init {
        if (apiKey.isBlank()) {
            _uiState.value = RecipeUiState.Error("API key is not configured. Please check your setup.")
            Log.e(TAG, "API key is not configured")
        } else {
            searchRecipes()
        }
    }
    
    fun searchRecipes(query: String? = null, type: String? = null) {
        if (apiKey.isBlank()) {
            _uiState.value = RecipeUiState.Error("API key is not configured. Please check your setup.")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading
            try {
                // Validate input
                val sanitizedQuery = query?.trim()?.takeIf { it.isNotBlank() }
                val mealType = type?.let { mapMealType(it) }
                
                val response = ApiClient.recipeApiService.searchRecipes(
                    apiKey = apiKey,
                    query = sanitizedQuery,
                    type = mealType,
                    number = DEFAULT_PAGE_SIZE
                )
                
                if (response.isSuccessful) {
                    val recipes = response.body()?.results ?: emptyList()
                    if (recipes.isEmpty()) {
                        _uiState.value = RecipeUiState.Error("No recipes found. Try a different search.")
                    } else {
                        _uiState.value = RecipeUiState.Success(recipes)
                    }
                } else {
                    val errorMessage = when (response.code()) {
                        401 -> "Invalid API key. Please check your configuration."
                        402 -> "API request limit reached. Please try again later."
                        404 -> "No recipes found for your search."
                        else -> "Failed to load recipes: ${response.message()}"
                    }
                    _uiState.value = RecipeUiState.Error(errorMessage)
                    Log.e(TAG, "API Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                val errorMsg = when (e) {
                    is java.net.UnknownHostException -> "No internet connection. Please check your network and try again."
                    is java.net.SocketTimeoutException -> "Connection timeout. Please try again later."
                    else -> "An error occurred: ${e.message ?: "Unknown error"}"
                }
                _uiState.value = RecipeUiState.Error(errorMsg)
                Log.e(TAG, "Error in searchRecipes", e)
            }
        }
    }
    
    private fun mapMealType(displayType: String): String? {
        return when (displayType.lowercase(Locale.ROOT)) {
            "main course" -> "main course"
            "breakfast" -> "breakfast"
            "dessert" -> "dessert"
            "smoothie" -> "drink"
            "salad" -> "salad"
            "soup" -> "soup"
            else -> null
        }
    }
}

sealed class RecipeUiState {
    object Loading : RecipeUiState()
    data class Success(val recipes: List<com.example.nutrifind.data.model.Recipe>) : RecipeUiState()
    data class Error(val message: String) : RecipeUiState()
}
