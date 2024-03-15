package com.sinyee.babybus.simpleUrlBuilderNoAppsFinalModeration

data class GameInfoData(val info: String, val userIdInfo: String, val push: String?)
internal data class UserData(val info: String, val userId: String)
internal data class Game(val gameList: List<String?>, val gameItem: String?)
internal data class GameData(val gameItems: String?, val gameItem: String?)
