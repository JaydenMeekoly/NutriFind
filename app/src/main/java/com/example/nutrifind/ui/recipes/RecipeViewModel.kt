package com.example.nutrifind.ui.recipes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.api.ApiClient
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _uiState = mutableStateOf<RecipeUiState>(RecipeUiState.Loading)
    val uiState: State<RecipeUiState> = _uiState
    
    private val apiKey = "9535fe6534be4f9c85bb979d117f11ec" // Replace with your actual API key
    
    fun searchRecipes(query: String? = null) {
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading
            try {
                val response = ApiClient.recipeApiService.searchRecipes(
                    apiKey = apiKey,
                    query = query,
                    number = 10
                )
                
                if (response.isSuccessful) {
                    _uiState.value = RecipeUiState.Success(response.body()?.results ?: emptyList())
                } else {
                    _uiState.value = RecipeUiState.Error("Failed to load recipes: ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = RecipeUiState.Error("An error occurred: ${e.message}")
            }
        }
    }
}

sealed class RecipeUiState {
    object Loading : RecipeUiState()
    data class Success(val recipes: List<com.example.nutrifind.data.model.Recipe>) : RecipeUiState()
    data class Error(val message: String) : RecipeUiState()
}
