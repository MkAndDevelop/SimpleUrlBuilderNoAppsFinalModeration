package com.sinyee.babybus.simpleurlbuilder

import com.sinyee.babybus.simpleurlbuilder.utils.AppConst
import com.sinyee.babybus.simpleurlbuilder.utils.decrypt
import java.net.URLEncoder

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

    fun getAppsFlyerData(
        appsFlyerDataMap: MutableMap<String, Any>?,
        campaign: String?
    ): ScoreGameData {
        val data = getAppsFlyerInfoString(appsFlyerDataMap, campaign)
        return ScoreGameData(data.first, data.second)
    }

    private fun getAppsFlyerInfoString(
        appsFlyerDataMap: MutableMap<String, Any>?,
        campaign: String?
    ): Pair<String, String?> {
        var appsFlyerCampaign: String? = null
        val appsFlyerData = collectAppsFlyerData(appsFlyerDataMap)
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

    private fun collectAppsFlyerData(data: MutableMap<String, Any>?): HashMap<String, String?> {
        val hashMap = HashMap<String, String?>()
        keys.forEach { key ->
            val value = URLEncoder.encode(data?.get(key).toString(), AppConst.UTF)
            hashMap[key] = value
        }
        return hashMap
    }
}