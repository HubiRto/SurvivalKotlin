package pl.pomoku.survivalkotlin.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import pl.pomoku.survivalkotlin.SurvivalKotlin.Companion.instance
import pl.pomoku.survivalkotlin.database.entity.AccountEntity
import pl.pomoku.survivalkotlin.database.services.AccountService

class PlayerJoin : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val accountService: AccountService = instance.accountService
        val player: Player = event.player
        val playerUUID: String = player.uniqueId.toString()
        var playerAccount = accountService.findByPlayerUUID(playerUUID)
        if (playerAccount == null) {
            playerAccount = AccountEntity(playerUUID, player.name, 0.0)
            accountService.save(playerAccount)
        }
    }
}