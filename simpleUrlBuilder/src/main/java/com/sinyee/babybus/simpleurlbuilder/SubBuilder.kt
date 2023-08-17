package com.sinyee.babybus.simpleurlbuilder

import com.sinyee.babybus.simpleurlbuilder.utils.AppConst
import com.sinyee.babybus.simpleurlbuilder.utils.decrypt

internal object SubBuilder {
    fun getSubData(campaign: String?): SubData {
        val subData = getSub(campaign)
        val subs = setSubs(subData.subs)
        val push = subData.push
        return SubData(subs, push)
    }

    private fun getSub(campaign: String?): Sub {
        return if (campaign != null) {
            val parts = campaign.split("_").toMutableList()
            if (parts.size < 10) {
                var size = parts.size
                while (size <= 10) {
                    parts.add("")
                    size++
                }
            }

            parts[10] = AppConst.FIRST_OPEN

            val push = if (parts[1] == "" || parts[1] == AppConst.NONE) null
            else parts[1]

            val subs =
                if (parts[0] == "" || parts[0] == AppConst.NONE) listOf(null) + parts.subList(2, parts.size)
                else listOf(parts[0]) + parts.subList(2, parts.size)

            Sub(subs, push)
        } else {
            val push: String? = null
            val subs = listOf(null, "", "", "", "", "", "", "", "", AppConst.FIRST_OPEN)
            Sub(subs, push)
        }
    }

    private fun setSubs(subs: List<String?>): String {
        val str = StringBuilder()
        var key = 1
        subs.forEachIndexed { _, item ->
            str.append("${"JnN1Yg==".decrypt()}$key=$item")
            key++
        }
        return str.toString()
    }
}