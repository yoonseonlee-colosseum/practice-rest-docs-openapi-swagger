package com.practice.practicerestdocs.domain

enum class BusinessCode(
    val code: String,
    val message: String,
    val remark: String = "",
) {
    // Common
    SUCCESS("0000", "정상"),

    // Client
    BAD_REQUEST("C400", "잘못된 요청입니다. 요청내용을 확인하세요."),

    // Application
    INTERNAL_SERVER_ERROR("S500", "시스템 내부오류가 발생했습니다. 담당자에게 문의바랍니다."),
}
