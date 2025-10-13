package com.practice.practicerestdocs.infrastructure.web.order

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

@Schema(description = "주문생성명령")
data class OrderCreateCommand(
    @Schema(description = "회원번호", required = true)
    @field:NotBlank
    val memberNo: String,

    @Schema(description = "상품식별번호목록", required = true, example = "[1,2,]")
    @field:NotEmpty
    val productIds: List<Long>,
)
