package com.threegap.bitnagil.security

import javax.crypto.SecretKey

interface KeyProvider {
    fun getKey(): SecretKey
}
