package com.apex.apextest.utils

import timber.log.Timber

fun installTimber(isDebug: Boolean = true) {
    if (isDebug) {
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, "FarmFlow_$tag", message, t)
            }

            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format(
                    "Class: %s - Method: %s - Line: %s - Message: %s",
                    element.className,
                    element.methodName,
                    element.lineNumber,
                    super.createStackElementTag(element)
                )
            }
        })
    } else {
        Timber.plant(TimberReleaseTree())
    }
}