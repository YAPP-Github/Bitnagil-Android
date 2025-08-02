package com.threegap.bitnagil.datastore.auth.serializer

import androidx.datastore.core.Serializer
import com.threegap.bitnagil.datastore.auth.model.AuthToken

interface AuthTokenSerializer : Serializer<AuthToken>
