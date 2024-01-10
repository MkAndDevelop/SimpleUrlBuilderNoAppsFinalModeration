package com.sinyee.babybus.simpleurlbuilder

import android.app.Activity
import com.sinyee.babybus.simpleurlbuilder.sdk.AppsFlayerDataBuilder
import com.sinyee.babybus.simpleurlbuilder.sdk.referrer.ReferrerAccountId
import com.sinyee.babybus.simpleurlbuilder.sdk.referrer.ReferrerNoAppsData
import com.sinyee.babybus.simpleurlbuilder.utils.DomenHolder
import com.sinyee.babybus.simpleurlbuilder.utils.decrypt

object SimpleUrlBuilder {

    suspend fun build(
        tracker: String = DomenHolder.getRandomDome(),
        fbKey: String,
        devKey: String,
        battery: String? = null,
        facebookId: String,
        facebookToken: String,
        isDevSettings: Boolean? = null,
        context: Activity,
        isNoApps: Boolean = false
    ): GameInfoData {
        val deviceData = DeviceDataBuilder(
            battery = battery.toString(),
            isDevSettings = isDevSettings,
            context = context,
            isApps = isNoApps,
            devKey = devKey,
            facebookId = facebookId,
            facebookToken = facebookToken,
        ).getDeviceInfoUseCase()

        if (!isNoApps) {
            val appsFlyerData =
                AppsFlayerDataBuilder.getAppsFlyerData(activity = context, devKey = devKey)
            val campaign = appsFlyerData.score
            val appsFlyerStr = appsFlyerData.info
            val deviceDataStr = deviceData.info
            val afUserId = deviceData.userId
            val subsData = SubBuilder.getSubData(campaign)
            val push = subsData.gameItem
            val subsStr = subsData.gameItems
            val pushStr = "${"JnB1c2g9".decrypt()}$push"
            val referrerAccountId = ReferrerAccountId(context).accountId(fbKey)
            val url = "$tracker$referrerAccountId$appsFlyerStr$deviceDataStr$subsStr$pushStr"
            return GameInfoData(info = url, userIdInfo = afUserId, push = push)
        } else {
            val referrerData = ReferrerNoAppsData(context).getReferrerData(fbKey)
            val campaign = referrerData.campaign
            val appsFlyerStr = referrerData.info
            val deviceDataStr = deviceData.info
            val afUserId = deviceData.userId
            val subsData = SubBuilder.getSubData(campaign)
            val push = subsData.gameItem
            val subsStr = subsData.gameItems
            val pushStr = "${"JnB1c2g9".decrypt()}$push"
            val referrerAccountId = referrerData.accountId
            val url = "$tracker$referrerAccountId$appsFlyerStr$deviceDataStr$subsStr$pushStr"
            return GameInfoData(info = url, userIdInfo = afUserId, push = push)
        }
    }
}

