package com.example.presentation_layer.feature.chatacter.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.CharacterStatus
import com.example.domain_layer.usecase.character.FetchCharacterListUseCase
import com.example.domain_layer.usecase.character.FilterCharacterListByStatusUseCase
import com.example.domain_layer.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val fetchCharacterListUseCase: FetchCharacterListUseCase,
    private val filterCharacterListByStatusUseCase: FilterCharacterListByStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    init {
        fetchCharacterList()
    }
    private fun fetchCharacterList() {
        viewModelScope.launch {
            fetchCharacterListUseCase.fetchCharacterList()
                .onStart {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }
//                .catch {  } manejar exception
                .collect { result ->
                    when (result) {
                        is Either.Error -> {
                            _state.update {
                                it.copy(error = "Error", isLoading = false) // manejar error
                            }
                        }
                        is Either.Success -> {
                            _state.update {
                                it.copy(characters = result.data, isLoading = false)
                            }
                        }
                        else -> {}
                    }

                }
        }
    }

    fun onChipFilterAction(characterStatus: CharacterStatus) {
        viewModelScope.launch {
            if (characterStatus != CharacterStatus.ALL) {
                filterCharacterListByStatusUseCase.filterCharacterListByStatusUseCase(characterStatus.value.lowercase())
                    .onStart {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }
//                .catch {  } manejar exception
                    .collect { result ->
                        when (result) {
                            is Either.Error -> {
                                _state.update {
                                    it.copy(error = "Error", isLoading = false) // manejar error
                                }
                            }
                            is Either.Success -> {
                                _state.update {
                                    it.copy(characters = result.data, isLoading = false)
                                }
                            }

                            else -> {}

                        }
                    }
            } else {
                fetchCharacterList()
            }

        }
    }

}

data class CharacterListState(
    val characters: List<CharacterBo>? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)