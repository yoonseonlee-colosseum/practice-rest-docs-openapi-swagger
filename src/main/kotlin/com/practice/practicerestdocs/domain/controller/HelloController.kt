package com.practice.practicerestdocs.domain.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(): HelloResponse {
        val helloResponse = HelloResponse(
            1002,
            "content"
        )
        return helloResponse
    }
}

data class HelloResponse(
    val number: Int,
    val content: String,
)
