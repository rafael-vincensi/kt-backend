package com.rafaelvincensi.backend.connection

import com.rafaelvincensi.backend.connection.dto.ConnectionResponseDTO
import com.rafaelvincensi.backend.connection.dto.fromEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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
}