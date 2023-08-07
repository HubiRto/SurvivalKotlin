package pl.pomoku.survivalkotlin.database.services.impl

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import pl.pomoku.survivalkotlin.database.DatabaseManager
import pl.pomoku.survivalkotlin.database.entity.TimePlayerEntity
import pl.pomoku.survivalkotlin.database.services.TimePlayerService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.*

class TimePlayerServiceImpl(private val plugin: JavaPlugin, private val databaseManager: DatabaseManager) :
    TimePlayerService {
    private val tableName = "time_player"
    override fun createTable() {
        val fileName = "sql/create_table_time_player.sql"
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

    override fun insert(entity: TimePlayerEntity) {
        val query = "INSERT INTO $tableName (player_uuid, today_time, collected_rewards, received_all) VALUES (?,?,?,?)"
        try {
            val connection = databaseManager.getConnection() ?: return
            val statement: PreparedStatement = connection.prepareStatement(query)
            statement.setString(1, entity.playerUUID)
            statement.setLong(2, entity.todayTime)
            statement.setInt(3, entity.collectedRewards)
            statement.setBoolean(4, entity.receivedAll)
            statement.executeUpdate()
            statement.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    override fun getAllWithReceivedAllFalse(): List<TimePlayerEntity>? {
        return getAll()?.filter { !it.receivedAll }
    }

    override fun getAll(): List<TimePlayerEntity>? {
        val list: MutableList<TimePlayerEntity> = ArrayList()
        val query = "SELECT * FROM $tableName"
        try {
            val connection = databaseManager.getConnection() ?: return null
            val statement: PreparedStatement = connection.prepareStatement(query)
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                list.add(
                    TimePlayerEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("player_uuid"),
                        resultSet.getLong("today_time"),
                        resultSet.getInt("collected_rewards"),
                        resultSet.getBoolean("received_all")
                    )
                )
            }
            resultSet.close()
            statement.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return list
    }

    override fun batchUpdate(listOfEntity: List<TimePlayerEntity>) {
        listOfEntity.forEach { timePlayer -> update(timePlayer) }
    }

    override fun update(entity: TimePlayerEntity) {
        val query = "UPDATE $tableName SET today_time = ?, collected_rewards = ?, received_all = ? WHERE id = ?"
        try {
            val connection = databaseManager.getConnection() ?: return
            val statement = connection.prepareStatement(query)
            statement.setLong(1, entity.todayTime)
            statement.setInt(2, entity.collectedRewards)
            statement.setBoolean(3, entity.receivedAll)
            entity.id?.let { statement.setInt(4, it) }
            statement.executeUpdate()
            statement.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    override fun getByPlayerUUID(playerUUID: String): TimePlayerEntity? {
        val query = "SELECT * FROM $tableName WHERE player_uuid = ?"
        var timePlayer: TimePlayerEntity? = null
        try {
            val connection = databaseManager.getConnection() ?: return null
            val statement = connection.prepareStatement(query)
            statement.setString(1, playerUUID)
            val resultSet = statement.executeQuery()
            if (resultSet.next()) {
                timePlayer = TimePlayerEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("player_uuid"),
                    resultSet.getLong("today_time"),
                    resultSet.getInt("collected_rewards"),
                    resultSet.getBoolean("received_all")
                )
            }

            resultSet.close()
            statement.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return timePlayer
    }

    override fun getByPlayerUUID(playerUUID: UUID): TimePlayerEntity? {
        return getByPlayerUUID(playerUUID.toString())
    }

    override fun getByPlayer(player: Player): TimePlayerEntity? {
        return getByPlayerUUID(player.uniqueId)
    }
}