package com.practice.practicerestdocs.infrastructure.web.order

import com.practice.practicerestdocs.application.order.OrderService
import com.practice.practicerestdocs.domain.order.OrderInfo
import com.practice.practicerestdocs.infrastructure.web.common.payload.ApiResponse
import com.practice.practicerestdocs.infrastructure.web.common.payload.ApiResponseGenerator
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderRestController(
    private val orderService: OrderService,
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/orders")
    fun create(
        @Valid @RequestBody createCommand: OrderCreateCommand,
    ): ApiResponse<OrderInfo> {
        return ApiResponseGenerator.success(orderService.create(createCommand));
    }
}
