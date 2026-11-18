package com.rafaelvincensi.backend.user.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateUserRequestDTO(

    @field: NotBlank(message = "Username cannot be empty!")
    @field:Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    val username: String,

    @field: NotBlank(message = "Email cannot be empty!")
    val email: String,

    @field: NotBlank(message = "Password cannot be empty!")
    @Size(min = 6, message = "Password must be at least 6 characters long!")
    val password: String
) {}