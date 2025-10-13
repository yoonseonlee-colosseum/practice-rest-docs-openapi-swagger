package com.practice.practicerestdocs.domain.product

import java.time.LocalDateTime

data class ProductInfo(
    val id: Long,
    val productName: String,
    val productNo: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(product: Product) = ProductInfo(
            id = product.id,
            productName = product.productName,
            productNo = product.productNo,
            product.createdAt
        )
    }
}
