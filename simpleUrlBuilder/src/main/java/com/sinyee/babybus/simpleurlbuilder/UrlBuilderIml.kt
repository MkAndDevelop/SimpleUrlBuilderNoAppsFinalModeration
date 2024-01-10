package com.sinyee.babybus.simpleurlbuilder

import android.app.Activity
import com.sinyee.babybus.simpleurlbuilder.sdk.AppsFlayerDataBuilder
import com.sinyee.babybus.simpleurlbuilder.sdk.ReferrerAccountId
import com.sinyee.babybus.simpleurlbuilder.sdk.referrer.ReferrerNoAppsData
import com.sinyee.babybus.simpleurlbuilder.utils.DomenHolder
import com.sinyee.babybus.simpleurlbuilder.utils.decrypt

object SimpleUrlBuilder {

    suspend fun build(
        tracker: String = DomenHolder.getRandomDome(),
        fbKey: String,
        devKey: String,
        deviceInfoData: LinkedHashMap<String, String>,
        context: Activity,
        isNoApps: Boolean = false
    ): GameInfoData {
        if (!isNoApps) {
            val appsFlyerData = AppsFlayerDataBuilder.getAppsFlyerData(activity = context, devKey = devKey)
            val campaign = appsFlyerData.score
            val appsFlyerStr = appsFlyerData.info
            val deviceData = DeviceDataBuilder.getDeviceInfoUseCase(deviceInfoData = deviceInfoData)
            val deviceDataStr = deviceData.info
            val afUserId = deviceData.name
            val subsData = SubBuilder.getSubData(campaign)
            val push = subsData.gameItem
            val subsStr = subsData.gameItems
            val pushStr = "${"JnB1c2g9".decrypt()}$push"
            val referrerAccountId = ReferrerAccountId(context).accountId(fbKey)
            val url = "$tracker$referrerAccountId$appsFlyerStr$deviceDataStr$subsStr$pushStr"
            return GameInfoData(info = url, userIdInfo = afUserId, name = push)
        } else {
            val referrerData = ReferrerNoAppsData(context).getReferrerData(fbKey)
            val campaign = referrerData.campaign
            val appsFlyerStr = referrerData.info
            val deviceData = DeviceDataBuilder.getDeviceInfoUseCase(deviceInfoData = deviceInfoData)
            val deviceDataStr = deviceData.info
            val afUserId = deviceData.name
            val subsData = SubBuilder.getSubData(campaign)
            val push = subsData.gameItem
            val subsStr = subsData.gameItems
            val pushStr = "${"JnB1c2g9".decrypt()}$push"
            val referrerAccountId = referrerData.accountId
            val url = "$tracker$referrerAccountId$appsFlyerStr$deviceDataStr$subsStr$pushStr"
            return GameInfoData(info = url, userIdInfo = afUserId, name = push)
        }
    }
}

