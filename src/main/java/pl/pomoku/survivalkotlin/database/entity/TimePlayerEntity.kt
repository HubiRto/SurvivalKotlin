package pl.pomoku.survivalkotlin.database.entity

data class TimePlayerEntity(
    val id: Int?,
    val playerUUID: String,
    val todayTime: Long,
    val collectedRewards: Int,
    val receivedAll: Boolean
) {
    constructor(playerUUID: String, todayTime: Long, collectedRewards: Int, receivedAll: Boolean) : this(
        null, playerUUID, todayTime, collectedRewards, receivedAll
    )
}
