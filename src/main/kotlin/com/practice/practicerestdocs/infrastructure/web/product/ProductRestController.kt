package com.practice.practicerestdocs.infrastructure.web.product

import com.practice.practicerestdocs.application.ProductService
import com.practice.practicerestdocs.domain.product.ProductInfo
import com.practice.practicerestdocs.infrastructure.web.common.payload.ApiResponse
import com.practice.practicerestdocs.infrastructure.web.common.payload.ApiResponseGenerator
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductRestController(
    private val productService: ProductService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/products")
    fun createProduct(
        @Valid @RequestBody createCommand: ProductCreateCommand
    ): ApiResponse<ProductInfo> {
        return ApiResponseGenerator.success(
            productService.create(createCommand)
        )
    }
}
