package pl.pomoku.survivalkotlin.commands.gamemodeCmd.modeSub

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.pomoku.pomokupluginsrepository.text.Text.strToComp
import pl.pomoku.survivalkotlin.SurvivalKotlin.Companion.instance
import pl.pomoku.survivalkotlin.commandManagerLib.SubCommand

class SurvivalSub : SubCommand {
    override fun getName(): String {
        return "0"
    }

    override fun getDescription(): String {
        return "Zmienia tryb gry na przetrwania"
    }

    override fun getSyntax(): String {
        return "/gm 0 {player}"
    }

    override fun getPermission(): String {
        return "gm.survival"
    }

    override fun getTabCompletion(index: Int, args: Array<String>): MutableList<String>? {
        return when (index) {
            0 -> Bukkit.getOnlinePlayers().stream().map { it.name }.toList()
            else -> {
                null
            }
        }
    }

    override fun perform(sender: CommandSender, args: Array<String>) {
        val prefix = instance.PREFIX
        if (sender !is Player) {
            if (args.isEmpty()) {
                sender.sendMessage(strToComp("$prefix<red>Nie możesz wykonać tej komendy z konsoli."))
            } else if (args.size == 1) {
                val target: Player? = Bukkit.getPlayer(args[0])
                if (target == null) {
                    sender.sendMessage(strToComp("$prefix<red>Nie ma takiego gracza na serwerze."))
                } else {
                    target.gameMode = GameMode.SURVIVAL
                    sender.sendMessage(strToComp("$prefix<gray>Zmieniłeś tryb gracza <aqua>${target.name}</aqua> na <light_purple>SURVIVAL"))
                }
            } else {
                sender.sendMessage(strToComp("$prefix<red>Użycie: <gray>" + getSyntax()))
            }
        } else {
            if (args.isEmpty()) {
                sender.gameMode = GameMode.SURVIVAL
            } else if (args.size == 1) {
                val target: Player? = Bukkit.getPlayer(args[0])
                if (target == null) {
                    sender.sendMessage(strToComp("$prefix<red>Nie ma takiego gracza na serwerze."))
                } else {
                    if (sender == target) {
                        sender.sendMessage(strToComp("$prefix<gray>Zamiast tego użyj komendy <red>/gm 0"))
                    } else {
                        target.gameMode = GameMode.SURVIVAL
                        sender.sendMessage(strToComp("$prefix<gray>Zmieniłeś tryb gracza <aqua>${target.name}</aqua> na <light_purple>SURVIVAL"))
                    }
                }
            } else {
                sender.sendMessage(strToComp("$prefix<red>Użycie: <gray>" + getSyntax()))
            }
        }
    }
}