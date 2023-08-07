package pl.pomoku.survivalkotlin.commands.moenyCmd

import pl.pomoku.survivalkotlin.commandManagerLib.MainCommand
import pl.pomoku.survivalkotlin.commandManagerLib.argumentMatchers.ContainingAllCharsOfStringArgumentMatcher
import pl.pomoku.survivalkotlin.commands.moenyCmd.sub.MoneySingleCmd

class MoneyMainCmd : MainCommand(
    "<red>Nie masz uprawnień do wykonania tej komendy.",
    ContainingAllCharsOfStringArgumentMatcher())
{
    override fun registerSubCommands() {
        subCommands.add(MoneySingleCmd())
    }



}
