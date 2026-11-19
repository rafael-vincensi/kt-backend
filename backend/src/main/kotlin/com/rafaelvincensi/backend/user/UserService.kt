package com.rafaelvincensi.backend.user

import com.rafaelvincensi.backend.exception.ConflictException
import com.rafaelvincensi.backend.exception.NotFoundException
import com.rafaelvincensi.backend.user.dto.CreateUserRequestDTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(request: CreateUserRequestDTO): User {
        val normalizedUsername = request.username.trim().lowercase()
        val normalizedEmail = request.email.trim().lowercase()

        if (userRepository.existsUserByUsername(normalizedUsername)){
            throw ConflictException("Username already exists!")
        }

        if (userRepository.existsUserByEmail(normalizedEmail)){
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


}