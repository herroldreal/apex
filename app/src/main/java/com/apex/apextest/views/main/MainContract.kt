package com.apex.apextest.views.main

import androidx.paging.PagingData
import com.apex.domain.models.CharacterBO
import kotlinx.coroutines.flow.Flow

/**
 * UI State that represents MainScreen
 **/
sealed class MainState {
    object Loading : MainState()
    data class Error(val message: String) : MainState()
    data class Success(val characters: Flow<PagingData<CharacterBO>>) : MainState()
    data class ShowDetail(val character: Flow<CharacterBO>) : MainState()
}

/**
 * Main Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class MainActions(
    val onClick: (Int) -> Unit = {},
    val toCharacterDetail: (Int?) -> Unit = {},
)