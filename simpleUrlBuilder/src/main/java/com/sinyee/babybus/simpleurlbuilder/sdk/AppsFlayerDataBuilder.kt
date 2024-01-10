package com.sinyee.babybus.simpleurlbuilder.sdk

import android.app.Activity
import com.appsflyer.AFLogger
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.sinyee.babybus.simpleurlbuilder.ScoreGameData
import com.sinyee.babybus.simpleurlbuilder.utils.AppConst
import com.sinyee.babybus.simpleurlbuilder.utils.decrypt
import kotlinx.coroutines.isActive
import java.net.URLEncoder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal object AppsFlayerDataBuilder {
    private val keys = arrayOf(
        AppConst.AF_STATUS,
        AppConst.CAMPAIGN,
        AppConst.MEDIA_SOURCE,
        AppConst.AF_CHANNEL,
        AppConst.AF_AD,
        AppConst.CAMPAIGN_ID,
        AppConst.ADSET_ID,
        AppConst.AD_ID,
        AppConst.ADSET
    )

    private suspend fun appsFlyerData(activity: Activity, devKey: String): Map<String, Any>? =
        suspendCoroutine { res ->
            val appsInstance = AppsFlyerLib.getInstance()
            appsInstance.setLogLevel(AFLogger.LogLevel.NONE)
            appsInstance.setCollectAndroidID(false)
            appsInstance.init(
                devKey,
                object : AppsFlyerConversionListener {
                    override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                        if (res.context.isActive) res.resume(data)
                    }

                    override fun onConversionDataFail(data: String?) {
                        res.resume(null)
                    }

                    override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                        res.resume(null)
                    }

                    override fun onAttributionFailure(data: String?) {
                        res.resume(null)
                    }

                }, activity
            ).start(activity)
        }

    suspend fun getAppsFlyerData(
        devKey: String,
        activity: Activity
    ): ScoreGameData {
        val data = getAppsFlyerInfoString(devKey = devKey, activity = activity)
        return ScoreGameData(data.first, data.second)
    }

    private suspend fun getAppsFlyerInfoString(
        activity: Activity,
        devKey: String
    ): Pair<String, String?> {
        var appsFlyerCampaign: String? = null
        val appsFlyerData =  collectAppsFlyerData(appsFlyerData(activity = activity, devKey = devKey))
        val campaign = Facebook(activity).deepLink()
        val str = StringBuilder()
        appsFlyerData.forEach { key ->
            if (key.key != AppConst.CAMPAIGN) {
                str.append("&${key.key}=${key.value}")
            } else if (campaign != null) {
                appsFlyerCampaign = campaign.substringAfter("Oi8v".decrypt())
                val encodedCampaign = URLEncoder.encode(campaign, AppConst.UTF)
                str.append("&${AppConst.CAMPAIGN}=$encodedCampaign")
            } else {
                appsFlyerCampaign = appsFlyerData[key.key].toString()
                str.append("&${key.key}=${key.value}")
            }
        }
        return Pair(str.toString(), appsFlyerCampaign)
    }

    private fun collectAppsFlyerData(data: Map<String, Any>?): HashMap<String, String?> {
        val hashMap = HashMap<String, String?>()
        keys.forEach { key ->
            val value = URLEncoder.encode(data?.get(key).toString(), AppConst.UTF)
            hashMap[key] = value
        }
        return hashMap
    }
}