package com.apex.apextest.views.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.apex.apextest.base.BaseViewModel
import com.apex.domain.usecases.DetailCharacterUseCase
import com.apex.domain.usecases.FetchAllCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val fetchAllCharactersUseCase: FetchAllCharactersUseCase,
    private val detailCharactersUseCase: DetailCharacterUseCase,
) : BaseViewModel() {

    private val _stateFlow: MutableStateFlow<MainState> = MutableStateFlow(MainState.Loading)
    val stateFlow: StateFlow<MainState> = _stateFlow.asStateFlow()

    fun fetchAllCharacters() {
        executeUseCase(
            action = {
                _stateFlow.update { MainState.Loading }
                val characters = fetchAllCharactersUseCase.execute(null)
                    .cachedIn(viewModelScope)
                _stateFlow.update { MainState.Sucess(characters) }
            },
            exceptionHandler = {
                _stateFlow.update { state -> MainState.Error(it.message ?: "Error") }
            },
        )
    }

    fun showCharacterDetail(characterId: Int) {
        executeUseCase(
            action = {
                _stateFlow.update { MainState.Loading }
                val characters = detailCharactersUseCase.execute(characterId)
                _stateFlow.update { MainState.ShowDetail(characters) }
            },
            exceptionHandler = {
                _stateFlow.update { state -> MainState.Error(it.message ?: "Error") }
            },
        )
    }
}