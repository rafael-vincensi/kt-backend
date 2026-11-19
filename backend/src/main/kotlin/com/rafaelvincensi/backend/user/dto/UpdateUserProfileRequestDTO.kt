package com.rafaelvincensi.backend.user.dto

data class UpdateUserProfileRequestDTO(
    val username: String? = null,
    val name: String? = null,
    val bio: String? = null,
)
