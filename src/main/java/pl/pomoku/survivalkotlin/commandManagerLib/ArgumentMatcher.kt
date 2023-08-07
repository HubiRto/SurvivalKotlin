package pl.pomoku.survivalkotlin.commandManagerLib

interface ArgumentMatcher {
    fun filter(tabCompletions: List<String>, argument: String): List<String>
}