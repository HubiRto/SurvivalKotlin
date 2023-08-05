package pl.pomoku.survivalkotlinplugin.database.services

import pl.pomoku.survivalkotlinplugin.database.entity.AccountEntity

interface AccountService {
    fun findByPlayerUUID(playerUUID: String) : AccountEntity?
    fun save(account: AccountEntity)
    fun createTable(tableName: String)
}