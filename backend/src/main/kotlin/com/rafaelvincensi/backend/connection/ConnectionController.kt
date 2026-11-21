package com.rafaelvincensi.backend.connection

import com.rafaelvincensi.backend.connection.dto.ConnectionResponseDTO
import com.rafaelvincensi.backend.connection.dto.fromEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/connections")
class ConnectionController(
    private val connectionService: ConnectionService,
) {

    @PostMapping("/{addresseeId}")
    fun sendConnectionRequest(@RequestParam requesterId: UUID, @PathVariable addresseeId: UUID): ResponseEntity<ConnectionResponseDTO> {
        val newConnection = connectionService.sendConnectionRequest(requesterId, addresseeId)
        val response = newConnection.fromEntity()

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PatchMapping("/{connectionId}/accept")
    fun acceptConnectionRequest(@RequestParam userId: UUID, @PathVariable connectionId: UUID): ResponseEntity<ConnectionResponseDTO> {
        val acceptConnection = connectionService.acceptConnectionRequest(userId, connectionId)
        val response = acceptConnection.fromEntity()

        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{connectionId}/decline")
    fun declineConnectionRequest(@RequestParam userId: UUID, @PathVariable connectionId: UUID): ResponseEntity<ConnectionResponseDTO> {
        val declineConnection = connectionService.declineConnectionRequest(userId, connectionId)
        val response = declineConnection.fromEntity()

        return ResponseEntity.ok(response)
    }

    @GetMapping("/{userId}/received")
    fun listPendingConnections(@PathVariable userId: UUID): ResponseEntity<List<ConnectionResponseDTO>> {
        val listPendingConnections = connectionService.listPendingConnections(userId)

        return ResponseEntity.ok(listPendingConnections)
    }

    @GetMapping("/{userId}/send")
    fun listPendingRequestConnections(@PathVariable userId: UUID): ResponseEntity<List<ConnectionResponseDTO>> {
        val listPendingRequestConnection = connectionService.listPendingRequestConnections(userId)

        return ResponseEntity.ok(listPendingRequestConnection)
    }

}