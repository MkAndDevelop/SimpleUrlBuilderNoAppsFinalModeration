package com.sinyee.babybus.simpleurlbuilder

import com.sinyee.babybus.simpleurlbuilder.utils.decrypt

object SimpleUrlBuilder {

    fun build(
        tracker: String,
        referrerAccountId: String,
        facebook: String?,
        appsFlyerDataMap: MutableMap<String, Any>?,
        deviceInfoData: LinkedHashMap<String, String>
    ): GameInfoData {
        val appsFlyerData = AppsFlayerDataBuilder.getAppsFlyerData(appsFlyerDataMap = appsFlyerDataMap, campaign = facebook)
        val campaign = appsFlyerData.score
        val appsFlyerStr = appsFlyerData.info
        val deviceData = DeviceDataBuilder.getDeviceInfoUseCase(deviceInfoData = deviceInfoData)
        val deviceDataStr = deviceData.info
        val afUserId = deviceData.name
        val subsData = SubBuilder.getSubData(campaign)
        val push = subsData.gameItem
        val subsStr = subsData.gameItems
        val pushStr = "${"JnB1c2g9".decrypt()}$push"
        val url = "$tracker$referrerAccountId$appsFlyerStr$deviceDataStr$subsStr$pushStr"
        return GameInfoData(info = url, userIdInfo = afUserId, name = push)
    }
}

