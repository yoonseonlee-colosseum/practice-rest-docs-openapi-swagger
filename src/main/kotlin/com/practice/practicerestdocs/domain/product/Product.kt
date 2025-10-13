package com.practice.practicerestdocs.domain.product

import java.time.LocalDateTime

class Product(
    val id: Long,
    val productName: String,
    val productNo: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {
    constructor(
        id: Long,
        productName: String,
        productNo: String,
    ) : this(
        id = id,
        productName = productName,
        productNo = productNo,

        createdAt = LocalDateTime.now(),
        modifiedAt = LocalDateTime.now(),
    )
}
