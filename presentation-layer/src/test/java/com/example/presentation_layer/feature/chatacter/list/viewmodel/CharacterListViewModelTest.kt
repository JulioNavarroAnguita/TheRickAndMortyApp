package com.example.presentation_layer.feature.chatacter.list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.CharacterLocationBo
import com.example.domain_layer.model.character.CharacterOriginBo
import com.example.domain_layer.model.character.CharacterStatus
import com.example.domain_layer.model.common.FailureBo
import com.example.domain_layer.usecase.character.FetchCharacterListUseCase
import com.example.domain_layer.utils.Either
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterListViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val fetchCharacterListUseCase: FetchCharacterListUseCase = mockk(relaxed = true)
    private lateinit var viewModel: CharacterListViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = CharacterListViewModel(
            fetchCharacterListUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchCharacterListEmitsDataStateWhenFetchIsSuccessful() = testScope.runTest {
        // Given
        val characters = listOf(
            CharacterBo(
                created = "",
                id = 1,
                name = "Rick",
                status = CharacterStatus.ALIVE,
                species = "Human",
                type = "",
                episodes = listOf(),
                gender = "tacimates",
                image = "suas",
                location = CharacterLocationBo(
                    name = "Ruben Hudson",
                    url = "https://duckduckgo.com/?q=commodo"
                ),
                origin = CharacterOriginBo(
                    name = "Carmen Maxwell",
                    url = "https://duckduckgo.com/?q=dicta"
                ),
                url = "https://www.google.com/#q=veri",
            )
        )
        // When
        coEvery { fetchCharacterListUseCase.fetchCharacterList(any()) } returns flow {
            emit(Either.Success(data = characters))
        }

        // Then
        viewModel.state.test {
            viewModel.fetchCharacterList()
            assert(awaitItem() is CharacterListState.Loading)
            val dataState = awaitItem() as CharacterListState.Data
            assert(dataState.characters == characters)
        }
    }


    @Test
    fun fetchCharacterListEmitsErrorStateWhenFetchFails() = testScope.runTest {
        // Given

        // When
        coEvery { fetchCharacterListUseCase.fetchCharacterList(any()) } returns flow {
            emit(
                Either.Error(
                    error = FailureBo.ServerError(
                        code = 500,
                        message = "Error to load characters"
                    )
                )
            )
        }
        // Then
        viewModel.state.test {
            viewModel.fetchCharacterList()
            assert(awaitItem() is CharacterListState.Loading)
            assert((awaitItem() as CharacterListState.Error).message == "Error to load characters")
        }
    }
}
