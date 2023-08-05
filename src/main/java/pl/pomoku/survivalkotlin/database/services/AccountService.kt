package pl.pomoku.survivalkotlin.database.services

import pl.pomoku.survivalkotlin.database.entity.AccountEntity


interface AccountService {
    fun findByPlayerUUID(playerUUID: String): AccountEntity?
    fun save(account: AccountEntity)
    fun createTable(tableName: String)
}