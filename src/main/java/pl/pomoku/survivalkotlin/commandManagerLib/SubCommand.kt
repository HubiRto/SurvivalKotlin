package pl.pomoku.survivalkotlin.commandManagerLib

import org.bukkit.command.CommandSender

interface SubCommand {
    fun getName(): String
    fun getDescription(): String
    fun getSyntax(): String
    fun getPermission(): String?
    fun getTabCompletion(index: Int, args: Array<String>): MutableList<String>?
    fun perform(sender: CommandSender, args: Array<String>)
}