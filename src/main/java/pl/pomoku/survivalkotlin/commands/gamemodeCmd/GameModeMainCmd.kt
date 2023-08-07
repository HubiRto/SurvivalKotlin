package pl.pomoku.survivalkotlin.commands.gamemodeCmd

import pl.pomoku.survivalkotlin.commandManagerLib.MainCommand
import pl.pomoku.survivalkotlin.commandManagerLib.argumentMatchers.ContainingAllCharsOfStringArgumentMatcher
import pl.pomoku.survivalkotlin.commands.gamemodeCmd.modeSub.CreativeSub
import pl.pomoku.survivalkotlin.commands.gamemodeCmd.modeSub.SpectatorSub
import pl.pomoku.survivalkotlin.commands.gamemodeCmd.modeSub.SurvivalSub
import pl.pomoku.survivalkotlin.commands.moenyCmd.sub.MoneySingleCmd

class GameModeMainCmd : MainCommand(
    "<red>Nie masz uprawnień do wykonania tej komendy.",
    ContainingAllCharsOfStringArgumentMatcher()
)
{
    override fun registerSubCommands() {
        subCommands.add(CreativeSub())
        subCommands.add(SurvivalSub())
        subCommands.add(SpectatorSub())
    }

}