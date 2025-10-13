package com.practice.practicerestdocs.infrastructure.web.order

import com.practice.practicerestdocs.application.order.OrderService
import com.practice.practicerestdocs.domain.order.OrderInfo
import com.practice.practicerestdocs.infrastructure.web.common.payload.ApiResponse
import com.practice.practicerestdocs.infrastructure.web.common.payload.ApiResponseGenerator
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema

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
    @Operation(
        summary = "주문을 생성한다.",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "주문을 생성합니다.",
            required = true,
            content = arrayOf(Content(schema = Schema(implementation = OrderCreateCommand::class)))
        ),
        responses = [io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "정상적으로 생성완료"), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "회원번호 혹은 상품번호를 누락한 경우 발생",
            content = [Content(schema = Schema(example = "{\"code\":\"C400\",\"message\":\"잘못된 요청입니다. 요청내용을 확인하세요.\",\"data\":null}"))]
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "미구현")]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/orders")
    fun create(
        @Valid @RequestBody createCommand: OrderCreateCommand
    ): ApiResponse<OrderInfo> {
        return ApiResponseGenerator.success(orderService.create(createCommand));
    }
}
