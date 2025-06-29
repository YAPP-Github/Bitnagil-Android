package com.threegap.bitnagil.security.keystore

import javax.crypto.SecretKey

interface KeyProvider {
    fun getKey(): SecretKey
}
