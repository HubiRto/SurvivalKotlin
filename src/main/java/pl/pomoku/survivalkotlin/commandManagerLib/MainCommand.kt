package pl.pomoku.survivalkotlin.commandManagerLib

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.command.TabExecutor
import org.bukkit.plugin.java.JavaPlugin
import pl.pomoku.pomokupluginsrepository.text.Text.strToComp

abstract class MainCommand(noPermissionMessage: String, argumentMatcher: ArgumentMatcher) : TabExecutor {
    protected val subCommands: MutableSet<SubCommand?> = HashSet()
    protected val noPermMessage: String
    protected val argumentMatcher: ArgumentMatcher

    init {
        this.noPermMessage = noPermissionMessage
        this.argumentMatcher = argumentMatcher
        registerSubCommands()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            val singleSubCmd = singleSubCmd
            if (singleSubCmd != null) {
                if (singleSubCmd.getPermission() != null && !sender.hasPermission(singleSubCmd.getPermission()!!)) {
                    sender.sendMessage(strToComp(noPermMessage))
                    return false
                }
                singleSubCmd.perform(sender, args)
                return true
            }
            return false
        }

        val subCommand = subCommands.firstOrNull { it?.getName().equals(args[0], ignoreCase = true) } ?: singleSubCmd

        if (subCommand != null) {
            if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission()!!)) {
                return false
            }
            subCommand.perform(sender, args.drop(1).toTypedArray())
        } else {
            sender.sendMessage(strToComp(noPermMessage))
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        if (args.isEmpty()) return null
        if (args.size == 1) {
            val subCommandsTC = subCommands
                .filter { it?.getName()?.let { _ -> sender.hasPermission(it.getPermission()!!) } ?: false }
                .mapNotNull { it?.getName() }
            return getMatchingStrings(subCommandsTC, args.last(), argumentMatcher)
        }

        val subCommands = subCommands.firstOrNull { it?.getName().equals(args[0], ignoreCase = true) }
        val subCommandTB = subCommands?.getTabCompletion(args.size - 2, args)
        return subCommandTB?.let { getMatchingStrings(it, args.last(), argumentMatcher) }
    }

    fun registerMainCommand(main: JavaPlugin, cmdName: String?) {
        val cmd: PluginCommand? = cmdName?.let { main.getCommand(it) }
        cmd?.setExecutor(this)
        cmd?.tabCompleter = this
        cmd?.setPermissionMessage(noPermMessage)
    }

    protected abstract fun registerSubCommands()
    protected val singleSubCmd: SubCommand?
        get() = subCommands.firstOrNull { it?.getName().equals("", ignoreCase = true) }

    fun getSubCommandsSet(): Set<SubCommand?> {
        return HashSet(subCommands)
    }

    companion object {
        private fun getMatchingStrings(
            tabCompletions: List<String>?,
            arg: String?,
            argumentMatcher: ArgumentMatcher
        ): List<String>? {
            if (tabCompletions == null || arg == null) return null
            val result: List<String> = argumentMatcher.filter(tabCompletions, arg)
            return result.sorted()
        }
    }
}