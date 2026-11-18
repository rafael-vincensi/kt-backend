package com.rafaelvincensi.backend.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {

    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun existsUserByUsername(username: String): Boolean
    fun existsUserByEmail(email: String): Boolean
}