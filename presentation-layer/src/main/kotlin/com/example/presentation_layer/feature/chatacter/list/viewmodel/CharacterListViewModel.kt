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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val fetchCharacterListUseCase: FetchCharacterListUseCase,
    private val filterCharacterListByStatusUseCase: FilterCharacterListByStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterListState>(CharacterListState.Loading)
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    init {
        fetchCharacterList()
    }

    fun fetchCharacterList() {
        viewModelScope.launch {
            fetchCharacterListUseCase.fetchCharacterList()
                .catch { error ->
                    _state.update {
                        CharacterListState.Error(error.message ?: "Unknown error")
                    }
                }
                .collectLatest { result ->
                    when (result) {
                        is Either.Error -> {
                            _state.update {
                                CharacterListState.Error(message = "Error to load characters")
                            }
                        }

                        is Either.Success -> {
                            _state.update {
                                CharacterListState.Data(characters = result.data)
                            }
                        }
                    }
                }
        }
    }

    fun onChipFilterAction(characterStatus: CharacterStatus) {
        viewModelScope.launch {
            if (characterStatus != CharacterStatus.ALL) {
                filterCharacterListByStatusUseCase.filterCharacterListByStatusUseCase(
                    characterStatus.value.lowercase()
                ).catch { error ->
                    _state.update {
                        CharacterListState.Error(error.message ?: "Unknown error")
                    }
                }.collectLatest { result ->
                    when (result) {
                        is Either.Error -> {
                            _state.update {
                                CharacterListState.Error(message = "Error to load characters filtered")
                            }
                        }

                        is Either.Success -> {
                            _state.update {
                                CharacterListState.Data(characters = result.data)
                            }
                        }
                    }
                }
            } else {
                fetchCharacterList()
            }
        }
    }
}

sealed class CharacterListState {
    data object Loading : CharacterListState()
    data class Error(val message: String? = null) : CharacterListState()
    data class Data(val characters: List<CharacterBo>? = null) : CharacterListState()
}