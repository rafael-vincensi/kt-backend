package com.rafaelvincensi.backend.user.dto

import com.rafaelvincensi.backend.user.User
import java.time.LocalDateTime
import java.util.UUID

data class UserResponseDTO(
    val userId: UUID,
    val username: String,
    val name: String?,
    val bio: String?,
    val email: String,
    val createdAt: LocalDateTime?
)

    fun User.fromEntity() = UserResponseDTO(
        userId = this.id!!,
        username = this.username,
        name = this.name,
        bio = this.bio,
        email = this.email,
        createdAt = this.createdAt
    )
