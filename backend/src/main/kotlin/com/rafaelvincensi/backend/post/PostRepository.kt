package com.rafaelvincensi.backend.post

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PostRepository: JpaRepository<Post, UUID> {

    fun findAllByUserIdOrderByCreatedAtDesc(userId: UUID): List<Post>
    fun countByUserId(userId: UUID): Long

}