package com.practice.practicerestdocs.infrastructure.web.product

import com.practice.practicerestdocs.annotation.RestDocsTest
import com.practice.practicerestdocs.application.ProductService
import com.practice.practicerestdocs.common.util.JsonUtils
import com.practice.practicerestdocs.config.Constant
import com.practice.practicerestdocs.documenation.MockMvcFactory
import com.practice.practicerestdocs.domain.product.ProductInfo
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


/**
 * ProductRestController 의 정상 플로우를 검증하고 API 문서화를 수행하는 테스트 클래스입니다.
 *
 * 목적:
 * - 성공 시(2xx) API 응답 구조 검증
 * - Spring REST Docs를 통한 API 문서 자동화
 *
 * 제외:
 * - 예외 및 유효성 검증은 ProductRestControllerTest 에서 다룹니다.
 */
@RestDocsTest
class ProductRestControllerDocsTest {

    @InjectMockKs
    lateinit var sut: ProductRestController

    @MockK
    lateinit var productService: ProductService

    @Test
    @DisplayName("생성: 상품")
    fun createProduct_success(contextProvider: RestDocumentationContextProvider) {

        val createCommandJson = """
                {
                    "productName": "테스트상품",
                    "productNo": "TEST-1111"
                }
                """.trimIndent()
        val createdProductJson = """
                {
                    "id": 1,
                    "productName": "테스트상품",
                    "productNo": "TEST-1111",
                    "createdAt": "2025-10-13T05:46:10"
                }
                """.trimIndent()

        every {
            productService.create(any())
        } returns JsonUtils.fromJson(createdProductJson, ProductInfo::class.java)


        val requestFieldDescription = arrayOf(
            PayloadDocumentation.fieldWithPath("productName").type(JsonFieldType.STRING).description("상품명"),
            PayloadDocumentation.fieldWithPath("productNo").type(JsonFieldType.STRING).description("상품번호")
        )

        val responseFieldDescription = arrayOf<FieldDescriptor>(
            PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드(정상: 0000)"),
            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지(정상: OK)"),
            PayloadDocumentation.fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("상품일련번호"),
            PayloadDocumentation.fieldWithPath("data.productName").type(JsonFieldType.STRING).description("상품명"),
            PayloadDocumentation.fieldWithPath("data.productNo").type(JsonFieldType.STRING).description("상품번호"),
            PayloadDocumentation.fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일시"),
        )

        MockMvcFactory.getRestDocsMockMvc(contextProvider, Constant.HOST_LOCAL, sut)
            .perform(
                RestDocumentationRequestBuilders.post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createCommandJson)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(
                MockMvcFactory.document("products", requestFieldDescription, responseFieldDescription)
            )

    }
}

