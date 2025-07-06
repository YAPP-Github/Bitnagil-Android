package com.threegap.bitnagil.network.token

interface TokenProvider {
    suspend fun getToken(): String?
}
