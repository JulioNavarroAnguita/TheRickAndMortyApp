package com.example.data_layer.model.common

const val DEFAULT_STRING = "none"
const val DEFAULT_INTEGER = -1

data class InfoDto(
    val count: Int? = null,
    val next: String? = null,
    val pages: Int? = null,
    val prev: String? = null
)

sealed class FailureDto {
    data object NoNetwork : FailureDto()
    class ServerError(val code: Int, val message: String?) : FailureDto()
    class UnexpectedFailure(val code: Int, val localizedMessage: String?) : FailureDto()
    data object Unknown : FailureDto()
}