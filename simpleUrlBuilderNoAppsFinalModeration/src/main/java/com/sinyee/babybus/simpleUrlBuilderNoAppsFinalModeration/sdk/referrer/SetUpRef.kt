package com.sinyee.babybus.simpleUrlBuilderNoAppsFinalModeration.sdk.referrer

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class SetUpRef(context: Context) {
    private val referrerClient = InstallReferrerClient.newBuilder(context).build()
    suspend fun getRef(): String = suspendCoroutine {
        CoroutineScope(Dispatchers.IO).launch {
            if (it.context.isActive) it.resume("${setUpRef()}")
        }
    }

    private suspend fun setUpRef(): String? = suspendCoroutine {
        val stateListener = object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(p0: Int) {
                when (p0) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        val details = referrerClient.installReferrer
                        if (it.context.isActive) it.resume(details.installReferrer)
                    }

                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        it.resume(null)
                    }

                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        it.resume(null)
                    }

                    InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR -> {
                        it.resume(null)
                    }

                    InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED -> {
                        it.resume(null)
                    }
                }
                try {
                    referrerClient.endConnection()
                } catch (_: Exception) {
                }
            }

            override fun onInstallReferrerServiceDisconnected() = it.resume(null)
        }
        try {
            CoroutineScope(Dispatchers.IO).launch {
                startConnection(referrerClient, stateListener)
            }
        } catch (e: Exception) {
            it.resume(null)
        }
    }

    private suspend fun startConnection(
        installReferrerClient: InstallReferrerClient,
        installReferrerStateListener: InstallReferrerStateListener
    ) {
        return suspendCancellableCoroutine {
            try {
                installReferrerClient.startConnection(installReferrerStateListener)
            } catch (e: Exception) {
                it.resumeWithException(e)
            }
        }
    }
}