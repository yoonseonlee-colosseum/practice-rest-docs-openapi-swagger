package com.practice.practicerestdocs.infrastructure.web.common.payload

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T? = null,
) {
    constructor(code: String, message: String) : this(code, message, null)
}
