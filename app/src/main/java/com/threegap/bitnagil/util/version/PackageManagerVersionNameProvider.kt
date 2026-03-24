package com.threegap.bitnagil.util.version

import android.content.Context
import com.threegap.bitnagil.presentation.util.version.VersionNameProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PackageManagerVersionNameProvider @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : VersionNameProvider {
    override fun getVersionName(): String =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()
}