package pl.pomoku.survivalkotlin.database.entity

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface AccountEntity : Entity<AccountEntity> {
    companion object : Entity.Factory<AccountEntity>()

    var id: Int
    var playerUUID: String
    var playerName: String
    var money: Double
}

object Accounts : Table<AccountEntity>("de_account") {
    val id = int("id").primaryKey()
    val playerUUID = varchar("player_uuid")
    val playerName = varchar("player_name")
    val money = double("money")
}