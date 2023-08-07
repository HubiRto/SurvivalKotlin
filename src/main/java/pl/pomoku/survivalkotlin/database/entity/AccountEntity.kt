package pl.pomoku.survivalkotlin.database.entity

data class AccountEntity(val id: Int?, val playerUUID: String, val playerName: String, val money: Double) {
    constructor(playerUUID: String, playerName: String, money: Double) : this(null, playerUUID, playerName, money)
}