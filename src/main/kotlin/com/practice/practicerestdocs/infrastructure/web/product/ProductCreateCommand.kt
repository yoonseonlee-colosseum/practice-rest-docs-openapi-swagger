package com.practice.practicerestdocs.infrastructure.web.product

import com.practice.practicerestdocs.domain.product.Product
import jakarta.validation.constraints.NotBlank

data class ProductCreateCommand(
    @field:NotBlank
    val productName: String,
    @field:NotBlank
    val productNo: String,
) {
    fun toProduct(id: Long) = Product(
        id = id,
        productName = productName,
        productNo = productNo,
    )
}
