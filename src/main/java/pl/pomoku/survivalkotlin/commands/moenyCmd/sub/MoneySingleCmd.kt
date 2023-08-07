package pl.pomoku.survivalkotlin.commands.moenyCmd.sub

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.pomoku.pomokupluginsrepository.text.Text.strToComp
import pl.pomoku.survivalkotlin.SurvivalKotlin.Companion.instance
import pl.pomoku.survivalkotlin.commandManagerLib.SubCommand
import pl.pomoku.survivalkotlin.database.entity.AccountEntity
import java.util.*

class MoneySingleCmd : SubCommand {
    override fun getName(): String {
        return ""
    }

    override fun getDescription(): String {
        return "Zwraca stan konta gracza"
    }

    override fun getSyntax(): String {
        return "/money"
    }

    override fun getPermission(): String? {
        return null
    }

    override fun getTabCompletion(index: Int, args: Array<String>): MutableList<String>? {
        return null
    }

    override fun perform(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            val player: Player = sender
            val playerUUID: UUID = player.uniqueId
            println(player)
            val playerAccount: AccountEntity =
                instance.accountService.findByPlayerUUID(playerUUID.toString()) ?: return
            val textColor = if (playerAccount.money >= 0) "<green>" else "<red>"
            player.sendMessage(strToComp(("<gray>Twój stan konta: " + textColor + playerAccount.money) + "<green>$"))
        }

    }
}