package com.threegap.bitnagil.domain.error.model

data class BitnagilError(
    val code: String,
    override val message: String,
) : Exception()
