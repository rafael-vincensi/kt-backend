package com.rafaelvincensi.backend.exception.dto

import java.time.LocalDateTime

data class ErrorDetails(
    val status: Int,
    val message: String?,
    val timestamp: LocalDateTime
)