package pl.pomoku.survivalkotlin.database.services

import org.bukkit.entity.Player
import pl.pomoku.survivalkotlin.database.entity.TimePlayerEntity
import java.util.UUID

interface TimePlayerService {
    fun createTable()
    fun insert(entity: TimePlayerEntity)
    fun getAllWithReceivedAllFalse(): List<TimePlayerEntity>?
    fun getAll(): List<TimePlayerEntity>?
    fun batchUpdate(listOfEntity: List<TimePlayerEntity>)
    fun update(entity: TimePlayerEntity)
    fun getByPlayerUUID(playerUUID: String): TimePlayerEntity?
    fun getByPlayerUUID(playerUUID: UUID): TimePlayerEntity?
    fun getByPlayer(player: Player): TimePlayerEntity?
}