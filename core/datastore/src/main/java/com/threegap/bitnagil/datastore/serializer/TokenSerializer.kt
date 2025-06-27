package com.threegap.bitnagil.datastore.serializer

import androidx.datastore.core.Serializer
import com.threegap.bitnagil.datastore.model.AuthToken

interface TokenSerializer : Serializer<AuthToken>
