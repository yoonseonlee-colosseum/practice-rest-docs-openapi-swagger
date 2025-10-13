package com.practice.practicerestdocs.infrastructure.web.common.handler

import com.practice.practicerestdocs.infrastructure.web.common.payload.ApiResponse
import com.practice.practicerestdocs.infrastructure.web.common.payload.ApiResponseGenerator
import com.practice.practicerestdocs.config.logger
import com.practice.practicerestdocs.domain.BusinessCode
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.lang.Exception

@RestControllerAdvice
class GlobalRestControllerExceptionHandler {

    private val logger = logger()

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        IllegalArgumentException::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        MethodArgumentTypeMismatchException::class,
        HttpMessageNotReadableException::class,
        HttpMediaTypeNotSupportedException::class,
        HttpMediaTypeNotAcceptableException::class,
        BindException::class
    )
    protected fun badRequestHandle(exception: Exception): ApiResponse<Unit> {
        logger.info { "[BadRequest] ${exception.message}" }
        when (exception) {
            is MethodArgumentNotValidException -> {
                exception.bindingResult.fieldErrors.forEach {
                    logger.warn {
                        "[ValidationError] field=${it.field}, value=${it.rejectedValue}, message=${it.defaultMessage}"
                    }
                }
            }

            is BindException -> {
                exception.bindingResult.fieldErrors.forEach {
                    logger.warn {
                        "[BindError] field=${it.field}, value=${it.rejectedValue}, message=${it.defaultMessage}"
                    }
                }
            }

            else -> {
                logger.warn { "[BadRequest:UnhandledType] ${exception.localizedMessage}" }
            }
        }
        return ApiResponseGenerator.fail(
            code = BusinessCode.BAD_REQUEST.code,
            message = BusinessCode.BAD_REQUEST.message,
        )
    }
}
