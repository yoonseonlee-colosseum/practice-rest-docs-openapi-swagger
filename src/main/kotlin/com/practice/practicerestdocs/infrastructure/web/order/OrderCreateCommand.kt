package com.practice.practicerestdocs.infrastructure.web.order

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class OrderCreateCommand(
    @field:NotBlank
    val memberNo: String,

    @field:NotEmpty
    val productIds: List<Long>,
)
