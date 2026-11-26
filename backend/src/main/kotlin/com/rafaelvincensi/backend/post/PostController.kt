package com.rafaelvincensi.backend.post

import com.rafaelvincensi.backend.post.dto.CreatePostRequestDTO
import com.rafaelvincensi.backend.post.dto.PostReponseDTO
import com.rafaelvincensi.backend.post.dto.UpdatePostRequestDTO
import com.rafaelvincensi.backend.post.dto.fromEntity
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(@RequestBody request: CreatePostRequestDTO, userId: UUID): ResponseEntity<PostReponseDTO> {
        val post = postService.createPost(request, userId)
        val response = post.fromEntity()

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/user/{userId}")
    fun listPosts(@PathVariable userId: UUID): ResponseEntity<List<PostReponseDTO>> {
        val posts = postService.listPosts(userId)

        return ResponseEntity.ok(posts)
    }

    @PatchMapping("/{postId}")
    fun updatePost(@PathVariable postId: UUID, userId: UUID, request: UpdatePostRequestDTO): ResponseEntity<PostReponseDTO> {
        val updatedPost = postService.updatePost(postId, userId, request)
        val response = updatedPost.fromEntity()

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: UUID, userId: UUID): ResponseEntity<Void> {
        postService.deletePost(userId, postId)

        return ResponseEntity.noContent().build()
    }

}