package com.rafaelvincensi.backend.post.dto

import com.rafaelvincensi.backend.post.Post
import java.time.LocalDateTime
import java.util.UUID

data class PostReponseDTO(
    val postId: UUID,
    val userId: UUID,
    val description: String?,
    val createdAt: LocalDateTime?,
    val updateAt: LocalDateTime?
)
fun Post.fromEntity() = PostReponseDTO (
    postId = this.id!!,
    userId = this.user.id!!,
    description = this.description,
    createdAt = this.createdAt,
    updateAt = this.updatedAt
    )

