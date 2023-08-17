package com.sinyee.babybus.simpleurlbuilder

import com.sinyee.babybus.simpleurlbuilder.utils.decrypt

object SimpleUrlBuilder {

    fun build(
        tracker: String,
        referrerAccountId: String,
        facebook: String?,
        appsFlyerDataMap: MutableMap<String, Any>?,
        deviceInfoData: LinkedHashMap<String, String>
    ): UrlData {
        val appsFlyerData = AppsFlayerDataBuilder.getAppsFlyerData(appsFlyerDataMap = appsFlyerDataMap, campaign = facebook)
        val campaign = appsFlyerData.campaign
        val appsFlyerStr = appsFlyerData.info
        val deviceData = DeviceDataBuilder.getDeviceInfoUseCase(deviceInfoData = deviceInfoData)
        val deviceDataStr = deviceData.deviceInfo
        val afUserId = deviceData.afUserId
        val subsData = SubBuilder.getSubData(campaign)
        val push = subsData.push
        val subsStr = subsData.subs
        val pushStr = "${"JnB1c2g9".decrypt()}$push"
        val url = "$tracker$referrerAccountId$appsFlyerStr$deviceDataStr$subsStr$pushStr"
        return UrlData(url = url, afUserId = afUserId, push = push)
    }
}

