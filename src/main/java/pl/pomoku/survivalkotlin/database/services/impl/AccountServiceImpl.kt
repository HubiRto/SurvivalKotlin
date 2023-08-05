package pl.pomoku.survivalkotlinplugin.database.services.impl

import org.bukkit.plugin.java.JavaPlugin
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import pl.pomoku.survivalkotlinplugin.database.DatabaseManager
import pl.pomoku.survivalkotlinplugin.database.entity.AccountEntity
import pl.pomoku.survivalkotlinplugin.database.entity.Accounts
import pl.pomoku.survivalkotlinplugin.database.services.AccountService
import java.lang.Exception

class AccountServiceImpl(private val plugin: JavaPlugin, private val databaseManager: DatabaseManager) : AccountService {
    override fun findByPlayerUUID(playerUUID: String): AccountEntity? {
        return databaseManager.getDatabase().sequenceOf(Accounts).firstOrNull { it.playerUUID eq playerUUID }
    }

    override fun save(account: AccountEntity) {
        val existingAccount = findByPlayerUUID(account.playerUUID)
        if (existingAccount != null) return
        databaseManager.getDatabase().insert(Accounts) {
            set(it.playerUUID, account.playerUUID)
            set(it.playerName, account.playerName)
            set(it.money, account.money)
        }
    }

    override fun createTable(tableName: String) {
        val sqlFilePath = "sql/create_table_account.sql"
        val sqlFile = this.javaClass.getResourceAsStream("/$sqlFilePath")
        if(sqlFile == null){
            plugin.logger.warning("Nie można odnaleźć pliku SQL: $sqlFilePath")
            return
        }

        val sqlStatment = sqlFile.reader().readText()
        sqlFile.close()

        try {
            databaseManager.getDatabase().useConnection { connection ->
                val statment = connection.createStatement()
                statment.executeUpdate(sqlStatment)
            }
            plugin.logger.info("Utworzono tabelę: $tableName")
        }catch (ex: Exception) {
            plugin.logger.severe("Błąd podczas tworzenia tabeli: $tableName")
            ex.printStackTrace()
        }
    }

}