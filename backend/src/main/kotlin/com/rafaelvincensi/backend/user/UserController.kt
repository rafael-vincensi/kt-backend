package com.rafaelvincensi.backend.user

import com.rafaelvincensi.backend.user.dto.CreateUserRequestDTO
import com.rafaelvincensi.backend.user.dto.UserResponseDTO
import com.rafaelvincensi.backend.user.dto.fromEntity
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@Valid @RequestBody request: CreateUserRequestDTO): ResponseEntity<UserResponseDTO> {
        val user = userService.createUser(request)
        val response = user.fromEntity()

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

}