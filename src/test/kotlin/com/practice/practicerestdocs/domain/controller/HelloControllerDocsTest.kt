package com.practice.practicerestdocs.domain.controller

import com.practice.practicerestdocs.annotation.RestDocsTest
import com.practice.practicerestdocs.config.Constant
import com.practice.practicerestdocs.documenation.MockMvcFactory
import com.practice.practicerestdocs.documenation.ResponseStatusHandler
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RestDocsTest
class HelloControllerDocsTest{

    @InjectMockKs
    lateinit var controller: HelloController

    @Test
    @DisplayName("hello 엔드포인트 문서화 - 정상")
    fun hello_success(contextProvider: RestDocumentationContextProvider) {

        MockMvcFactory.getRestDocsMockMvc(contextProvider, Constant.HOST_LOCAL, controller)
            .perform(
                RestDocumentationRequestBuilders.get("/hello")
            )
            .andExpect(status().isOk)
            .andDo(
                MockMvcFactory.document("hello")
            )
    }

    @Test
    @DisplayName("hello 엔드포인트 문서화 - 401")
    fun hello_fail_401(contextProvider: RestDocumentationContextProvider) {

        MockMvcFactory.getRestDocsMockMvc(contextProvider, Constant.HOST_LOCAL, controller)
            .perform(
                RestDocumentationRequestBuilders.get("/hello")
            )
            .andDo(ResponseStatusHandler(HttpStatus.UNAUTHORIZED))
            .andDo(
                MockMvcFactory.document("hello")
            )
    }
}
