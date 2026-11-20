package com.rafaelvincensi.backend.exception

import com.rafaelvincensi.backend.exception.dto.ErrorDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message,
            timestamp = LocalDateTime.now(),
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails)
    }

    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(ex: ConflictException): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            status = HttpStatus.CONFLICT.value(),
            message = ex.message,
            timestamp = LocalDateTime.now()
        )

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails)
    }

    @ExceptionHandler(AlreadyConnectedException::class)
    fun handleAlreadyConnectedException(ex: AlreadyConnectedException): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            status = HttpStatus.CONFLICT.value(),
            message = ex.message,
            timestamp = LocalDateTime.now()
        )

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails)
    }

    @ExceptionHandler(ConnectionRequestAlreadySentException::class)
    fun handleConnectionRequestAlreadySentException(ex: ConnectionRequestAlreadySentException): ResponseEntity<ErrorDetails> {
        var errorDetails = ErrorDetails(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message,
            timestamp = LocalDateTime.now()
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }
}