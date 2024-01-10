package com.sinyee.babybus.simpleurlbuilder.sdk

import android.content.Context
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class Facebook(private val context: Context) {

    private fun initFacebookSdk() {
        FacebookSdk.apply {
            setAdvertiserIDCollectionEnabled(true)
            setAutoInitEnabled(true)
            fullyInitialize()
        }
    }

    suspend fun deepLink(): String? =
        suspendCoroutine { deepLink ->
            initFacebookSdk()
            try {
                AppLinkData.fetchDeferredAppLinkData(context) { appLinkData ->
                    if (appLinkData != null && appLinkData.targetUri != null) {
                        val adta = appLinkData.targetUri.toString()
                        deepLink.resume(adta)
                    } else deepLink.resume(null)
                }
            } catch (e: Exception) {
                deepLink.resume(null)
            }
        }
}