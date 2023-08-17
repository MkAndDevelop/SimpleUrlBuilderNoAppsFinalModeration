package com.sinyee.babybus.simpleurlbuilder

data class UrlData(val url: String, val afUserId: String, val push: String?)
internal data class AppsFlyerData(val info: String?, val campaign: String?)
internal data class DeviceData(val deviceInfo: String, val afUserId: String)
internal data class Sub(val subs: List<String?>, val push: String?)
internal data class SubData(val subs: String?, val push: String?)
