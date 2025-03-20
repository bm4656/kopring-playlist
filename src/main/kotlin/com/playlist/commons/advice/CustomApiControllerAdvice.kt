package com.playlist.commons.advice

import com.playlist.commons.config.response.ApiResponse
import com.playlist.commons.config.response.CommonResponse
import com.playlist.commons.exception.CustomException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomApiControllerAdvice {

    private val log = LoggerFactory.getLogger(CustomApiControllerAdvice::class.java)

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<CommonResponse<String>> {
        log.info(e.message, e)

        return ApiResponse.fail(
            code = e.httpStatus.name,
            data = e.message,
            status = e.httpStatus
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<CommonResponse<String>> {
        log.info(e.message, e)

        val fieldError = e.bindingResult.fieldErrors.firstOrNull()
        val message = fieldError?.let { "[${it.field} ${it.defaultMessage}]" } ?: "유효성 검사 오류"

        return ApiResponse.fail(
            code = "VALIDATION_ERROR",
            data = message,
            status = HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<CommonResponse<String>> {
        log.info(e.message, e)

        return ApiResponse.fail(
            code = "INTERNAL_SERVER_ERROR",
            data = "시스템 오류가 발생했습니다.",
            status = HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
