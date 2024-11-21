package com.example.data_layer.model.common

import com.example.domain_layer.model.common.FailureBo
import com.example.domain_layer.model.common.InfoBo

fun InfoDto.toInfoBo() = InfoBo(
    count = count ?: DEFAULT_INTEGER,
    next = next ?: DEFAULT_STRING,
    pages = pages ?: DEFAULT_INTEGER,
    prev = prev ?: DEFAULT_STRING
)

fun FailureDto.failureDtoToBo(): FailureBo = when (this) {
    FailureDto.NoNetwork -> FailureBo.NoNetwork
    is FailureDto.ServerError -> FailureBo.ServerError(code = code, message = message ?: DEFAULT_STRING)
    is FailureDto.UnexpectedFailure -> FailureBo.UnexpectedFailure(code = code, localizedMessage = localizedMessage ?: DEFAULT_STRING)
    FailureDto.Unknown -> FailureBo.Unknown
}