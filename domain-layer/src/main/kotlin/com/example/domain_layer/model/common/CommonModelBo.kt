package com.example.domain_layer.model.common

data class InfoBo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)
sealed class FailureBo {
    data object NoNetwork : FailureBo()
    class EmptyResponse(val message: String) : FailureBo()
    class ServerError(val code: Int, val message: String) : FailureBo()
    class ClientError(val code: Int, val message: String) : FailureBo()
    class UnexpectedFailure(val code: Int, val localizedMessage: String) : FailureBo()
    data object Unknown : FailureBo()
}