package com.practice.practicerestdocs.infrastructure.web.common.payload

object ApiResponseGenerator {

    private val SUCCESS: ApiResponse<Unit> = ApiResponse("0000", "정상")
    private val FAIL: ApiResponse<Unit> = ApiResponse("S500", "시스템 내부오류가 발생했습니다. 담당자에게 문의바랍니다.")

    fun success(): ApiResponse<Unit> = SUCCESS

    fun <D> success(data: D): ApiResponse<D> = ApiResponse(SUCCESS.code, SUCCESS.message, data)

    fun fail(): ApiResponse<Unit> = FAIL

    fun <D> fail(data: D): ApiResponse<D> = ApiResponse(FAIL.code, FAIL.message, data)

    fun <D> fail(code: String, message: String): ApiResponse<D> = ApiResponse(code, message)
}
