package com.rafaelvincensi.backend.post

import com.rafaelvincensi.backend.exception.AccessDeniedException
import com.rafaelvincensi.backend.exception.NotFoundException
import com.rafaelvincensi.backend.exception.PostLimitExceededException
import com.rafaelvincensi.backend.post.dto.CreatePostRequestDTO
import com.rafaelvincensi.backend.post.dto.PostReponseDTO
import com.rafaelvincensi.backend.post.dto.UpdatePostRequestDTO
import com.rafaelvincensi.backend.post.dto.fromEntity
import com.rafaelvincensi.backend.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService
) {

    @Transactional
    fun createPost(request: CreatePostRequestDTO, userId: UUID): Post {
        val userPost = userService.findById(userId)
        val countPosts = postRepository.countByUserId(userId)

        if (countPosts >= 9) {
            throw PostLimitExceededException("You can have a maximum of 9 posts!")
        }
        val post = Post(
            description = request.description,
            createdAt = LocalDateTime.now(),
            user = userPost)

        return postRepository.save(post)
    }

    fun listPosts(userId: UUID): List<PostReponseDTO> =
        postRepository.findAllByUserIdOrderByCreatedAtDesc(userId).map { it.fromEntity() }

    @Transactional
    fun updatePost(postId: UUID, userId: UUID, request: UpdatePostRequestDTO): Post {
        val post = postRepository.findById(postId).orElseThrow{
            NotFoundException("Post not found!")
        }

        if (userId != post.user.id){
            throw AccessDeniedException("Only the post creator can edit!")
        }

        if (request.description != null) {
            post.description = request.description
        }

        post.updatedAt = LocalDateTime.now()

        return postRepository.save(post)
    }

    @Transactional
    fun deletePost(userId: UUID, postId: UUID) {
        val post = postRepository.findById(postId).orElseThrow{
            NotFoundException("Post not found!")
    }
        if (userId  != post.user.id) {
            throw AccessDeniedException("Only the post creator can delete!")
        }

        postRepository.delete(post)
    }
}