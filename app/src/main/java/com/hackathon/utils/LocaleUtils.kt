package com.hackathon.utils

import android.content.Context
import androidx.core.os.ConfigurationCompat
import java.util.*

fun Context.getCurrentLocale(): Locale {
    return ConfigurationCompat.getLocales(this.resources.configuration)[0]
}

fun Context.getCurrentLocaleCode(): String {
    val countryCode = getCurrentLocale().country
    val languageCode = getCurrentLocale().language
    return "${languageCode}_$countryCode"
}

fun Context.getCurrentTimezone(): String {
    return TimeZone.getDefault().id
}

