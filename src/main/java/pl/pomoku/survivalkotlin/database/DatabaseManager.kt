package pl.pomoku.survivalkotlinplugin.database

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.ktorm.database.Database

class DatabaseManager(private val plugin: JavaPlugin) {
    private lateinit var database: Database
    fun setupDatabase() {
        val config: FileConfiguration = plugin.config
        val group = "database"
        println(config.getString("$group.host"))

        val host = config.getString("$group.host") ?: "localhost"
        val port = config.getInt("$group.port")
        val databaseName = config.getString("$group..database_name") ?: "db"
        val username = config.getString("$group.username") ?: "user"
        val password = config.getString("$group.password") ?: "password"

        try {
            database = Database.connect(
                url = "jdbc:mariadb://$host:$port/$databaseName",
                driver = "org.mariadb.jdbc.Driver",
                user = username,
                password = password
            )
            plugin.logger.info("Pomyślnie podłączono z bazą danych.")
        }catch (ex: Exception){
            plugin.logger.severe("Błąd podczas łączenia z bazą danych: ${ex.message}")
            plugin.server.pluginManager.disablePlugin(plugin);
        }
    }

    fun getDatabase(): Database {
        return database;
    }
}