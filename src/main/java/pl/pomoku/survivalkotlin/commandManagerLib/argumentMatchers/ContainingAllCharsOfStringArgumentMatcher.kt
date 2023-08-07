package pl.pomoku.survivalkotlin.commandManagerLib.argumentMatchers

import pl.pomoku.survivalkotlin.commandManagerLib.ArgumentMatcher

class ContainingAllCharsOfStringArgumentMatcher : ArgumentMatcher {
    override fun filter(tabCompletions: List<String>, argument: String): List<String> {
        val result: MutableList<String> = ArrayList()
        for (tabCompletion in tabCompletions) {
            var passes = true
            for (c in argument.toCharArray()) {
                passes = tabCompletion.contains(c.toString())
                if (!passes) break
            }
            if (passes) result.add(tabCompletion)
        }
        return result
    }
}
