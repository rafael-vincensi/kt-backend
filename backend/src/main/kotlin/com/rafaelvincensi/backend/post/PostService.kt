package com.rafaelvincensi.backend.post

import com.rafaelvincensi.backend.exception.AccessDeniedException
import com.rafaelvincensi.backend.exception.NotFoundException
import com.rafaelvincensi.backend.post.dto.CreatePostRequestDTO
import com.rafaelvincensi.backend.post.dto.UpdatePostRequestDTO
import com.rafaelvincensi.backend.user.UserService
import org.springframework.data.crossstore.ChangeSetPersister
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

    fun updatePost(postId: UUID, userId: UUID, request: UpdatePostRequestDTO): Post {
        val user = userService.findById(userId)
        val post = postRepository.findById(postId).orElseThrow{
            NotFoundException("Post not found!")
        }

        if (user.id != post.user.id){
            AccessDeniedException("Only the post creator can edit!")
        }

        if (request.description != null) {
            post.description = request.description
        }

        post.updatedAt = LocalDateTime.now()

        return postRepository.save(post)
    }
}