package com.practice.practicerestdocs.domain.order

import java.time.LocalDateTime

data class OrderInfo(
    val id: Long,
    val memberNo: String,
    val orderNo: String,
    val createdAt: LocalDateTime,
)
