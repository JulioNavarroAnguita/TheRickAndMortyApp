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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val fetchCharacterListUseCase: FetchCharacterListUseCase,
    private val filterCharacterListByStatusUseCase: FilterCharacterListByStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> get() = _state.asStateFlow()

    init {
        fetchCharacterList()
    }
    private fun fetchCharacterList() {
        viewModelScope.launch {
            fetchCharacterListUseCase.fetchCharacterList()
//                .onStart {  }
//                .catch {  }
                .collect { result ->
                    when (result) {
                        is Either.Error -> {}
                        is Either.Success -> {
                            _state.update {
                                it.copy(characterData = CharacterDataModel(characterList = result.data))
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
                    .collect { result ->
                        when (result) {
                            is Either.Error -> {}
                            is Either.Success -> {
                                _state.update {
                                    it.copy(characterData = CharacterDataModel(characterList = result.data))
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
    val characterData: CharacterDataModel? = null,
)

data class CharacterDataModel(
    val characterList: List<CharacterBo>
)