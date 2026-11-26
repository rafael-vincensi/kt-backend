package com.rafaelvincensi.backend.post.dto

import jakarta.validation.constraints.Size

data class UpdatePostRequestDTO(

    @field: Size(max = 140, message = "Description can have a maximum of 140 characters!")
    val description: String? = null
)
