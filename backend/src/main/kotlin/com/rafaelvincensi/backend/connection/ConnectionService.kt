package com.rafaelvincensi.backend.connection

import com.rafaelvincensi.backend.exception.AlreadyConnectedException
import com.rafaelvincensi.backend.exception.ConnectionRequestAlreadySentException
import com.rafaelvincensi.backend.exception.SelfConnectionNotAllowedException
import com.rafaelvincensi.backend.user.UserService
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class ConnectionService(
    private val connectionRepository: ConnectionRepository,
    private val userService: UserService
) {

    @Transactional
    fun sendConnectionRequest(requesterId: UUID, addresseeId: UUID): Connection {

        if (requesterId == addresseeId) {
            throw SelfConnectionNotAllowedException("You cannot connect to yourself!")
        }

        val requester = userService.findById(requesterId)
        val addressee = userService.findById(addresseeId)

        val alreadyConnected = connectionRepository.existsByRequesterIdAndAddresseeIdAndStatusRequest(
            requesterId,
            addresseeId,
            StatusRequest.ACCEPTED) || (connectionRepository.existsByRequesterIdAndAddresseeIdAndStatusRequest(
            addresseeId,
            requesterId,
            StatusRequest.ACCEPTED))

        val pendingConnection = connectionRepository.existsByRequesterIdAndAddresseeIdAndStatusRequest(requesterId,
            addresseeId,
            StatusRequest.PENDING) || connectionRepository.existsByRequesterIdAndAddresseeIdAndStatusRequest(
            addresseeId,
            requesterId,
            StatusRequest.PENDING)

        if (alreadyConnected){
            throw AlreadyConnectedException("You have already connected with this profile!")
        }

        if (pendingConnection){
            throw ConnectionRequestAlreadySentException("You have already sent a connection request to this profile!")
        }

        val connection = Connection(
            requester = requester,
            addressee = addressee,
            statusRequest = StatusRequest.PENDING,
            createdAt = LocalDateTime.now()
        )

        return connectionRepository.save(connection)
    }

}