package com.example.presentation_layer.feature.location.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.LocationResultBo
import com.example.domain_layer.usecase.location.FetchAllLocationsUseCase
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
class LocationListViewModel @Inject constructor(
    private val fetchAllLocationsUseCase: FetchAllLocationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LocationListState>(LocationListState.Loading)
    val state: StateFlow<LocationListState> get() = _state.asStateFlow()

    init {
        fetchAllLocations()
    }

    private fun fetchAllLocations() {
        viewModelScope.launch {
            fetchAllLocationsUseCase.fetchAllLocations()
                .catch { error ->
                    _state.update {
                        LocationListState.Error(error.message ?: "Unknown error")
                    }
                }
                .collectLatest { result ->
                    when (result) {
                        is Either.Error -> {
                            _state.update {
                                LocationListState.Error(message = "Error to load locations")
                            }
                        }

                        is Either.Success -> {
                            _state.update {
                                LocationListState.Data(locations = result.data)
                            }
                        }
                    }
                }
        }
    }
}

sealed class LocationListState {
    data object Loading : LocationListState()
    data class Error(val message: String) : LocationListState()
    data class Data(val locations: List<LocationResultBo.LocationBo>) : LocationListState()
}