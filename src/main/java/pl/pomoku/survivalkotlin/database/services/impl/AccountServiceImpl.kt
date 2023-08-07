package pl.pomoku.survivalkotlin.database.services.impl

import org.bukkit.plugin.java.JavaPlugin
import pl.pomoku.survivalkotlin.database.DatabaseManager
import pl.pomoku.survivalkotlin.database.entity.AccountEntity
import pl.pomoku.survivalkotlin.database.services.AccountService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class AccountServiceImpl(private val plugin: JavaPlugin, private val databaseManager: DatabaseManager) :
    AccountService {

    override fun findByPlayerUUID(playerUUID: String): AccountEntity? {
        var entity: AccountEntity? = null
        val query = "SELECT * FROM account WHERE player_uuid = ?";

        try {
            val connection = databaseManager.getConnection() ?: return null
            val statement: PreparedStatement = connection.prepareStatement(query)
            statement.setString(1, playerUUID)
            val resultSet: ResultSet = statement.executeQuery()

            if (resultSet.next()) {
                val id = resultSet.getInt("id")
                val playerName = resultSet.getString("player_name")
                val money = resultSet.getDouble("money")
                entity = AccountEntity(id, playerUUID, playerName, money)
            }

            resultSet.close()
            statement.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return entity
    }

    override fun save(account: AccountEntity) {
        val query = "INSERT INTO account (player_uuid, player_name, money) VALUES (?,?,?)"
        try {
            val connection = databaseManager.getConnection() ?: return
            val statement: PreparedStatement =
                connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
            statement.setString(1, account.playerUUID)
            statement.setString(2, account.playerName)
            statement.setDouble(3, account.money)
            statement.executeUpdate()
            statement.close()
        }catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    override fun createTable(tableName: String) {
        val fileName = "sql/create_table_account.sql"
        try {
            val connection = databaseManager.getConnection() ?: return
            val inputStream = plugin.javaClass.getResourceAsStream("/$fileName")
            if (inputStream != null) {
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val query = bufferedReader.readText()

                val statement: PreparedStatement = connection.prepareStatement(query)
                statement.execute()
                statement.close()
                plugin.logger.info("Stworzono tabelę -> ($tableName)")
            } else {
                plugin.logger.severe("Nie można odnaleźć pliku: $fileName")
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }
}