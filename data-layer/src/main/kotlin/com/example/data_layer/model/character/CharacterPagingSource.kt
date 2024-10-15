package com.example.data_layer.model.character

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data_layer.datasource.character.CharacterRemoteDataSource
import com.example.domain_layer.model.character.CharacterBo
import okio.IOException
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(private val characterRemoteDataSource: CharacterRemoteDataSource) :
    PagingSource<Int, CharacterBo>() {
    //    override fun getRefreshKey(state: PagingState<Int, CharacterDto>) = state.anchorPosition
    override fun getRefreshKey(state: PagingState<Int, CharacterBo>) =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterBo> {
        return try {
           /* val page = params.key ?: 1
            val response = characterRemoteDataSource.getCharactersFromService(page)

//            val prevKey = if (page > 0) page - 1 else null
//            val nextKey = if (response.info?.next != null) page + 1 else null

            LoadResult.Page(
                data = response.characterList?.map { it.characterDtoToBo() } ?: listOf(),
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (response.info?.next != null) page + 1 else null
            )*/
            LoadResult.Error(Throwable())

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }
}