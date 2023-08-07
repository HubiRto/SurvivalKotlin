package pl.pomoku.survivalkotlin.commandManagerLib.argumentMatchers

import pl.pomoku.survivalkotlin.commandManagerLib.ArgumentMatcher
import java.util.stream.Collectors

class ContainingStringArgumentMatcher : ArgumentMatcher {
    override fun filter(tabCompletions: List<String>, argument: String): List<String> {
        return tabCompletions.stream().filter { tabCompletion: String -> tabCompletion.contains(argument) }
            .collect(Collectors.toList())
    }
}
