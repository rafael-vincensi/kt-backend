package com.rafaelvincensi.backend.connection

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ConnectionRepository: JpaRepository<Connection, UUID> {

    fun findAllByAddresseeIdAndStatusRequest(addresseeId: UUID, statusRequest: StatusRequest): List<Connection>
    fun findAllByRequesterIdAndStatusRequest(requesterId: UUID, statusRequest: StatusRequest): List<Connection>
    fun existsByRequesterIdAndAddresseeIdAndStatusRequest(requesterId: UUID, addresseeId: UUID, statusRequest: StatusRequest): Boolean
}