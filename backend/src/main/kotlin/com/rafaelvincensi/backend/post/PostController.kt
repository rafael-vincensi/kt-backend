package com.rafaelvincensi.backend.post

import com.rafaelvincensi.backend.post.dto.CreatePostRequestDTO
import com.rafaelvincensi.backend.post.dto.PostReponseDTO
import com.rafaelvincensi.backend.post.dto.fromEntity
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(request: CreatePostRequestDTO, userId: UUID): ResponseEntity<PostReponseDTO> {
        val post = postService.createPost(request, userId)
        val response = post.fromEntity()

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/user/{userId}")
    fun listPosts(@PathVariable userId: UUID): ResponseEntity<List<PostReponseDTO>> {
        val posts = postService.listPosts(userId)

        return ResponseEntity.ok(posts)
    }

}