package com.practice.practicerestdocs.infrastructure.web.product

import com.practice.practicerestdocs.application.ProductService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


/**
 * ProductRestController 의 예외 케이스를 검증하는 테스트 클래스
 *
 * 목적:
 * - 입력값 검증(@Valid) 및 예외 응답 포맷 검증
 * - 비정상 요청(400, 404, 409 등)에 대한 컨트롤러 동작 확인
 *
 * 제외:
 * - 정상 응답 및 API 문서화는 ProductRestControllerDocsTest 에서 검증합니다.
 */
@WebMvcTest(ProductRestController::class)
class ProductRestControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var productService: ProductService

    @DisplayName("생성: 상품명을 누락한 경우 400 오류")
    @Test
    fun createProductWhenProductNameMissingTest() {
        val requestContent = """
                {
                    "productName": "",
                    "productNo": "1234"
                }                
                """.trimIndent()
        mockMvc.perform(
            RestDocumentationRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError())
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("""
                        {
                            "code": "C400",
                            "message": "잘못된 요청입니다. 요청내용을 확인하세요.",
                            "data": null
                        }
                        """.trimIndent()
                    )
            )
    }
}
