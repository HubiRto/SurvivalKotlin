package pl.pomoku.survivalkotlin

import lombok.Getter
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections
import pl.pomoku.survivalkotlin.commandManagerLib.MainCommand
import pl.pomoku.survivalkotlin.commands.gamemodeCmd.GameModeMainCmd
import pl.pomoku.survivalkotlin.commands.moenyCmd.MoneyMainCmd
import pl.pomoku.survivalkotlin.database.DatabaseManager
import pl.pomoku.survivalkotlin.database.services.AccountService
import pl.pomoku.survivalkotlin.database.services.TimePlayerService
import pl.pomoku.survivalkotlin.database.services.impl.AccountServiceImpl
import pl.pomoku.survivalkotlin.database.services.impl.TimePlayerServiceImpl
import pl.pomoku.survivalkotlin.utils.ConfigFileManager

@Getter
class SurvivalKotlin : JavaPlugin() {
    private lateinit var databaseManager: DatabaseManager
    lateinit var accountService: AccountService
    lateinit var timePlayerService: TimePlayerService
    lateinit var PREFIX: String

    //COMMANDS
    private lateinit var moneyCmd: MainCommand
    private lateinit var gameModeCmd: MainCommand

    //CONFIG
    lateinit var configFileManager: ConfigFileManager

    private fun setPrefix(): String {
        val config = configFileManager.getConfiguration("config")
        val mode = config?.getBoolean("prefix.mode") ?: false
        val text = config?.getString("prefix.text") ?: ""
        return if (mode) text else ""
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        configFileManager = ConfigFileManager(this)
        configFileManager.createDefaultFile()
        configFileManager.createFile("database")

        databaseManager = DatabaseManager(this)
        databaseManager.connect()

        PREFIX = setPrefix()

        accountService = AccountServiceImpl(this, databaseManager)
        accountService.createTable("account")

        timePlayerService = TimePlayerServiceImpl(this, databaseManager)
        timePlayerService.createTable()


        loadListeners()

        moneyCmd = MoneyMainCmd()
        moneyCmd.registerMainCommand(this, "money")

        gameModeCmd = GameModeMainCmd()
        gameModeCmd.registerMainCommand(this, "gm")
    }

    override fun onDisable() {
        databaseManager.disconnect()
    }

    companion object {
        lateinit var instance: SurvivalKotlin
            private set
    }

    private fun loadListeners() {
        val packageName = javaClass.packageName
        val reflections = Reflections("$packageName.listeners")

        val listenerClasses = reflections.getSubTypesOf(Listener::class.java)
        for (clazz in listenerClasses) {
            try {
                val listener = clazz.getDeclaredConstructor().newInstance() as Listener
                server.pluginManager.registerEvents(listener, this)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}
