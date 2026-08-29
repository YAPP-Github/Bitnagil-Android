package com.threegap.bitnagil.analytics.meta

import android.content.Context
import android.os.Bundle
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.threegap.bitnagil.analytics.AnalyticsLogger

class MetaAnalyticsLogger(private val context: Context) : AnalyticsLogger {

    private val logger by lazy { AppEventsLogger.newLogger(context) }

    override fun logOnBoardingCompleted() {
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_TUTORIAL)
    }

    override fun logFirstRoutineCompleted() {
        val parameters = Bundle().apply {
            putString(AppEventsConstants.EVENT_PARAM_LEVEL, LEVEL_FIRST_ROUTINE_COMPLETED)
        }

        logger.logEvent(AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL, parameters)
    }

    private companion object {
        const val LEVEL_FIRST_ROUTINE_COMPLETED = "first_routine_completed"
    }
}
