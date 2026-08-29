package com.threegap.bitnagil.analytics.meta

import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior

object MetaAnalyticsInitializer {
    fun initialize(isDebugBuild: Boolean) {
        if (!isDebugBuild) return

        FacebookSdk.setIsDebugEnabled(true)
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS)
    }
}
