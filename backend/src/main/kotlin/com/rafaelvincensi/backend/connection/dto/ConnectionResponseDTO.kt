package com.rafaelvincensi.backend.connection.dto

import com.rafaelvincensi.backend.connection.Connection
import com.rafaelvincensi.backend.connection.StatusRequest
import java.time.LocalDateTime
import java.util.UUID

data class ConnectionResponseDTO(
    val connectionId: UUID,
    val requesterId: UUID,
    val addresseeId: UUID,
    val statusRequest: StatusRequest,
    val createdAt: LocalDateTime,
    val respondedAt: LocalDateTime?
)

fun Connection.fromEntity() = ConnectionResponseDTO(
    connectionId = this.id!!,
    requesterId = this.requester.id!!,
    addresseeId = this.addressee.id!!,
    statusRequest = this.statusRequest,
    createdAt = this.createdAt,
    respondedAt = this.respondedAt
)