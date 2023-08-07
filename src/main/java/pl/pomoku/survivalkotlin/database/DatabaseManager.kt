package pl.pomoku.survivalkotlin.database

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.mariadb.jdbc.Connection
import pl.pomoku.survivalkotlin.SurvivalKotlin
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseManager(private val plugin: SurvivalKotlin) {
    private var connection: Connection? = null

    fun connect() {
        val config: FileConfiguration? = plugin.configFileManager.getConfiguration("database")

        val host = config?.getString("database.host") ?: "localhost"
        val port = config?.getInt("database.port")
        val databaseName = config?.getString("database.database_name") ?: "db"
        val username = config?.getString("database.username") ?: "user"
        val password = config?.getString("database.password") ?: "password"

        try {
            Class.forName("org.mariadb.jdbc.Driver")
            val url = "jdbc:mariadb://$host:$port/$databaseName"
            connection = DriverManager.getConnection(url, username, password) as Connection?
            plugin.logger.info("Pomyślnie podłączono z bazą danych.")
        } catch (ex: SQLException) {
            plugin.logger.severe("Błąd podczas łączenia z bazą danych: ${ex.message}")
            ex.printStackTrace()
        } catch (ex: ClassNotFoundException) {
            plugin.logger.severe("Błąd podczas łączenia z bazą danych: ${ex.message}")
            ex.printStackTrace()
        }
    }

    fun disconnect() {
        connection?.close()
        plugin.logger.info("Rozłączono bazę danych")
    }

    fun getConnection(): Connection? {
        return connection
    }
}