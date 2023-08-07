package pl.pomoku.survivalkotlin.commands.gamemodeCmd.modeSub

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.pomoku.pomokupluginsrepository.text.Text.strToComp
import pl.pomoku.survivalkotlin.commandManagerLib.SubCommand

class SpectatorSub : SubCommand {
    override fun getName(): String {
        return "2"
    }

    override fun getDescription(): String {
        return "Zmienia tryb gry na niewidzialny"
    }

    override fun getSyntax(): String {
        return "/gm 2 {player}"
    }

    override fun getPermission(): String {
        return "gm.spectator"
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
        if (sender !is Player) {
            if (args.isEmpty()) {
                sender.sendMessage(strToComp("<red>Nie możesz wykonać tej komendy z konsoli."))
            } else if (args.size == 1) {
                val target: Player? = Bukkit.getPlayer(args[0])
                if (target == null) {
                    sender.sendMessage(strToComp("<red>Nie ma takiego gracza na serwerze."))
                } else {
                    target.gameMode = GameMode.SPECTATOR
                    sender.sendMessage(strToComp("<gray>Zmieniłeś tryb gracza <aqua>${target.name}</aqua> na <light_purple>SPECTATOR"))
                }
            } else {
                sender.sendMessage(strToComp("<red>Użycie: <gray>" + getSyntax()))
            }
        } else {
            if (args.isEmpty()) {
                sender.gameMode = GameMode.SPECTATOR
            } else if (args.size == 1) {
                val target: Player? = Bukkit.getPlayer(args[0])
                if (target == null) {
                    sender.sendMessage(strToComp("<red>Nie ma takiego gracza na serwerze."))
                } else {
                    if (sender == target) {
                        sender.sendMessage(strToComp("<gray>Zamiast tego użyj komendy <red>/gm 2"))
                    } else {
                        target.gameMode = GameMode.SPECTATOR
                        sender.sendMessage(strToComp("<gray>Zmieniłeś tryb gracza <aqua>${target.name}</aqua> na <light_purple>SPECTATOR"))
                    }
                }
            } else {
                sender.sendMessage(strToComp("<red>Użycie: <gray>" + getSyntax()))
            }
        }
    }
}