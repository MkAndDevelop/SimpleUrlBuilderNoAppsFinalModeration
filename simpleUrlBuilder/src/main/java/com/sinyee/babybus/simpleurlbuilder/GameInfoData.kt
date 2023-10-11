package com.sinyee.babybus.simpleurlbuilder

data class GameInfoData(val info: String, val userIdInfo: String, val name: String?)
internal data class ScoreGameData(val info: String?, val score: String?)
internal data class UserData(val info: String, val name: String)
internal data class Game(val gameList: List<String?>, val gameItem: String?)
internal data class GameData(val gameItems: String?, val gameItem: String?)
