package pl.pomoku.survivalkotlin.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import pl.pomoku.survivalkotlin.SurvivalKotlin
import java.io.File
import java.io.IOException

class ConfigFileManager(private val plugin: SurvivalKotlin) {
    private val dataFolder: File = plugin.dataFolder
    private val customConfigs: MutableMap<String, File> = mutableMapOf()
    private val customConfigurations: MutableMap<String, FileConfiguration> = mutableMapOf()

    init {
        createDataFolder()
    }

    private fun createDataFolder() {
        if (!dataFolder.exists()) dataFolder.mkdirs()
    }

    fun createDefaultFile(){
        createFile("config")
    }

    fun createFile(name: String){
        if(customConfigs.containsKey(name) || customConfigurations.containsKey(name)) return
        val file = File(plugin.dataFolder, "$name.yml")
        if(!file.exists()){
            plugin.saveResource("$name.yml", false)
        }
        customConfigs[name] = file

        val config = YamlConfiguration()
        try {
            config.load(file)
        }catch (ex: IOException){
            ex.printStackTrace()
        }
        customConfigurations[name] = config
    }

    fun getConfiguration(name: String): FileConfiguration? {
        return customConfigurations[name]
    }
}