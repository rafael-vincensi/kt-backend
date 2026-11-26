package com.rafaelvincensi.backend.post

import com.rafaelvincensi.backend.post.dto.CreatePostRequestDTO
import com.rafaelvincensi.backend.post.dto.UpdatePostRequestDTO
import com.rafaelvincensi.backend.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService
) {

    fun createPost(request: CreatePostRequestDTO, userId: UUID): Post {
        val userPost = userService.findById(userId)

        val post = Post(
            description = request.description,
            createdAt = LocalDateTime.now(),
            user = userPost)

        return postRepository.save(post)
    }

}