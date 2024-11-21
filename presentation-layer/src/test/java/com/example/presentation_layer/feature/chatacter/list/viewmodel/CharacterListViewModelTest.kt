package com.example.presentation_layer.feature.chatacter.list.viewmodel/*
@ExperimentalCoroutinesApi
class CharacterListViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val fetchCharacterListUseCase: FetchCharacterListUseCase = mockk()
    private val filterCharacterUseCase: FilterCharacterUseCase = mockk()
    private val filterCharacterListByStatusUseCase: FilterCharacterListByStatusUseCase = mockk()
    private lateinit var viewModel: CharacterListViewModel

    @Before
    fun setUp() {
        viewModel = CharacterListViewModel(
            fetchCharacterListUseCase,
            filterCharacterUseCase,
            filterCharacterListByStatusUseCase
        )
        Dispatchers.setMain(Dispatchers.Unconfined)  // Configura el dispatcher para el hilo principal.

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()  // Restaura el dispatcher después de las pruebas.
    }


    @Test
    fun fetchCharacterList_emitsDataState_whenFetchIsSuccessful() = runTest {
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
        coEvery { fetchCharacterListUseCase.fetchCharacterList() } returns flow {
            emit(Either.Success(data = characters))
        }

        viewModel.state.test {
            assert(awaitItem() is CharacterListState.Loading)  // Verificando el estado de carga
            val dataState = awaitItem() as CharacterListState.Data
            assert(dataState.characters == characters)  // Verificando que los personajes coincidan
            cancelAndIgnoreRemainingEvents()  // Aseguramos que no hay más eventos pendientes
        }
    }


    @Test
    fun fetchCharacterList_emitsErrorState_whenFetchFails() = runTest {
        coEvery { fetchCharacterListUseCase.fetchCharacterList() } returns flow {
            emit(
                Either.Error(
                    error = FailureBo.ServerError(
                        code = 500,
                        message = "Error to load characters"
                    )
                )
            )
        }

        viewModel.state.test {
            assert(awaitItem() is CharacterListState.Loading)
            assert((awaitItem() as CharacterListState.Error).message == "Error to load characters")
        }
    }
}*/
