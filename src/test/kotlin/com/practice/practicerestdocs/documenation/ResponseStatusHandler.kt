package com.practice.practicerestdocs.documenation

import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultHandler

class ResponseStatusHandler(
    private val status: HttpStatus,
) : ResultHandler {
    override fun handle(result: MvcResult) {
        result.response.status = status.value()
    }
}
