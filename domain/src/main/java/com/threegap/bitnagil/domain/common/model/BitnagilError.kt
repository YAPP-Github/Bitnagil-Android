package com.threegap.bitnagil.domain.common.model

data class BitnagilError(
    val code: String,
    override val message: String,
) : Exception()
