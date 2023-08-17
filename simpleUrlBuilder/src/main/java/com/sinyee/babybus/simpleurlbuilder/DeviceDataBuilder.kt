package com.sinyee.babybus.simpleurlbuilder

import com.sinyee.babybus.simpleurlbuilder.utils.AppConst

internal object DeviceDataBuilder {

   fun getDeviceInfoUseCase(deviceInfoData: LinkedHashMap<String, String>): DeviceData {
        val str = StringBuilder()
        deviceInfoData.forEach { key ->
            str.append("&${key.key}=${key.value}")
        }
        return DeviceData(str.toString(), deviceInfoData[AppConst.AF_USER_ID].toString())
    }
}