package pl.pomoku.survivalkotlin.commandManagerLib.argumentMatchers

import org.bukkit.util.StringUtil
import pl.pomoku.survivalkotlin.commandManagerLib.ArgumentMatcher

class StartingWithStringArgumentMatcher : ArgumentMatcher {
    override fun filter(tabCompletions: List<String>, argument: String): List<String> {
        val result: MutableCollection<String> = ArrayList()
        StringUtil.copyPartialMatches(argument, tabCompletions, result)
        return result.toList()
    }
}
