package com.rafaelvincensi.backend.user

import com.rafaelvincensi.backend.exception.ConflictException
import com.rafaelvincensi.backend.exception.NotFoundException
import com.rafaelvincensi.backend.user.dto.CreateUserRequestDTO
import com.rafaelvincensi.backend.user.dto.UpdateUserProfileRequestDTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(request: CreateUserRequestDTO): User {
        val normalizedUsername = request.username.trim().lowercase()
        val normalizedEmail = request.email.trim().lowercase()

        if (userRepository.existsUserByUsername(normalizedUsername)) {
            throw ConflictException("Username already exists!")
        }

        if (userRepository.existsUserByEmail(normalizedEmail)) {
            throw ConflictException("Email already exists!")
        }
        val password = passwordEncoder.encode(request.password)

        val user = User(username = normalizedUsername, email = normalizedEmail, password = password!!)

        return userRepository.save(user)
    }

    fun findByEmail(email: String): User {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("User with this email not found!")

        return user
    }

    fun findById(id: UUID): User {
        val user = userRepository.findById(id).orElseThrow {
            NotFoundException("User not found")
        }

        return user
    }

    fun findByUsername(username: String): User {
        val normalizedUsername = username.trim().lowercase()

        val user = userRepository.findByUsername(normalizedUsername)
            ?: throw NotFoundException("User with this username not found!")

        return user
    }

    fun updateUserProfile(id: UUID, request: UpdateUserProfileRequestDTO): User {
        val user = findById(id)

        if (!request.username.isNullOrBlank()) {
            val normalizedUsername = request.username.trim().lowercase()
            val existingUser = userRepository.findByUsername(normalizedUsername)

            if (existingUser != null && existingUser.id != user.id) {
                throw ConflictException("Username already exists!")
            }
            user.username = normalizedUsername
        }

        if (request.name != null) {
            user.name = request.name
        }
        if (request.bio != null) {
            user.bio = request.bio
        }

        user.updatedAt= LocalDateTime.now()

        return userRepository.save(user)
    }

}