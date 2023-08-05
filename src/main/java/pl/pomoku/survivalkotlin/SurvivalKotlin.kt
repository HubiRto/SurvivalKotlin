package pl.pomoku.survivalkotlin

import org.bukkit.plugin.java.JavaPlugin
import pl.pomoku.survivalkotlinplugin.database.DatabaseManager

class SurvivalKotlin : JavaPlugin() {
    private lateinit var databaseManager: DatabaseManager
    override fun onEnable() {
        saveDefaultConfig()
        databaseManager = DatabaseManager(this)
        databaseManager.setupDatabase()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
