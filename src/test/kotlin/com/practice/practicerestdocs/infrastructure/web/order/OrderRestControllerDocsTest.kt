package com.practice.practicerestdocs.infrastructure.web.order

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.practice.practicerestdocs.annotation.RestDocsTest
import com.practice.practicerestdocs.application.order.OrderService
import com.practice.practicerestdocs.common.util.JsonUtils
import com.practice.practicerestdocs.config.Constant
import com.practice.practicerestdocs.documenation.MockMvcFactory
import com.practice.practicerestdocs.domain.order.OrderInfo
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

/**
 * OrderRestController 의 정상 플로우를 검증하고 API 문서화를 수행하는 테스트 클래스입니다.
 *
 * 목적:
 * - 성공 시(2xx) API 응답 구조 검증
 * - Spring REST Docs를 통한 API 문서 자동화
 *
 * 제외:
 * - 예외 및 유효성 검증은 OrderRestControllerTest 에서 다룹니다.
 */
@RestDocsTest
class OrderRestControllerDocsTest {

    @InjectMockKs
    lateinit var sut: OrderRestController

    @MockK
    lateinit var orderService: OrderService

    @Test
    @DisplayName("생성: 주문")
    fun createOrder_success(contextProvider: RestDocumentationContextProvider) {
        // given
        val createCommandJson = """
            {
                "memberNo": "MEMBER-1001",
                "productIds": [1, 2]
            }
        """.trimIndent()

        val createdOrderJson = """
            {
                "id": 1,
                "memberNo": "MEMBER-1001",
                "orderNo": "ORDER-1",
                "createdAt": "2025-10-13T12:00:00"
            }
        """.trimIndent()

        every {
            orderService.create(any())
        } returns JsonUtils.fromJson(createdOrderJson, OrderInfo::class.java)

        // when
        val requestFieldDescription = arrayOf(
            PayloadDocumentation.fieldWithPath("memberNo")
                .type(JsonFieldType.STRING)
                .description("회원번호"),
            PayloadDocumentation.fieldWithPath("productIds")
                .type(JsonFieldType.ARRAY)
                .description("상품식별번호목록")
        )

        val responseFieldDescription = arrayOf<FieldDescriptor>(
            PayloadDocumentation.fieldWithPath("code")
                .type(JsonFieldType.STRING)
                .description("응답코드(정상: 0000)"),
            PayloadDocumentation.fieldWithPath("message")
                .type(JsonFieldType.STRING)
                .description("응답메시지(정상: OK)"),
            PayloadDocumentation.fieldWithPath("data.id")
                .type(JsonFieldType.NUMBER)
                .description("주문일련번호"),
            PayloadDocumentation.fieldWithPath("data.memberNo")
                .type(JsonFieldType.STRING)
                .description("회원번호"),
            PayloadDocumentation.fieldWithPath("data.orderNo")
                .type(JsonFieldType.STRING)
                .description("주문번호"),
            PayloadDocumentation.fieldWithPath("data.createdAt")
                .type(JsonFieldType.STRING)
                .description("생성일시")
        )

        // then
        MockMvcFactory.getRestDocsMockMvc(contextProvider, Constant.HOST_LOCAL, sut)
            .perform(
                RestDocumentationRequestBuilders.post("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createCommandJson)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            // REST Docs (문서 스니펫 생성)
            .andDo(
                MockMvcRestDocumentation.document(
                    "orders",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    requestFields(*requestFieldDescription),
                    responseFields(*responseFieldDescription)
                )
            )
            // OpenAPI 3.0 (Swagger 문서용)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    "orders",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    requestFields(*requestFieldDescription),
                    responseFields(*responseFieldDescription)
                )
            )
    }
}

