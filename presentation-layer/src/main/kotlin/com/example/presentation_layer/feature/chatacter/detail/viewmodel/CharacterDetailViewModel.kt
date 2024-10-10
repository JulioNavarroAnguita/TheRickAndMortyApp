package com.example.presentation_layer.feature.chatacter.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.CharacterBo
import com.example.domain_layer.usecase.FetchCharacterDetailUseCase
import com.example.domain_layer.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchCharacterDetailUseCase: FetchCharacterDetailUseCase,
) : ViewModel() {

//    private val itemId: String = checkNotNull(savedStateHandle["itemId"])

    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> get() = _state.asStateFlow()

    fun fetchCharacterDetail(id: Int?) {
        id?.let {
            viewModelScope.launch {
                fetchCharacterDetailUseCase.fetchCharacterDetail(id = id)
//                .onStart {  }
//                .catch {  }
                    .collect { result ->
                        when (result) {
                            is Either.Error -> {}
                            is Either.Success -> {
                                _state.update {
                                    it.copy(characterData = CharacterDataModel(character = result.data))
                                }
                            }

                            else -> {}
                        }

                    }
            }

        }
    }

}

data class CharacterDetailState(
    val characterData: CharacterDataModel? = null,
)

data class CharacterDataModel(
    val character: CharacterBo
)