package com.playlist.commons.config.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class CommonResponse<T>(
    val result: Boolean,
    val code: String? = null,
    val data: T? = null
)

class ApiResponse<T>(body: CommonResponse<T>, status: HttpStatus) : ResponseEntity<CommonResponse<T>>(body, status) {
    companion object {
        fun <T> success(
            data: T? = null,
            code: String? = null
        ): ResponseEntity<CommonResponse<T>> {
            val response = CommonResponse(result = true, code = code, data = data)
            return ok(response)
        }

        fun <T> fail(
            code: String? = null,
            data: T? = null,
            status: HttpStatus = HttpStatus.BAD_REQUEST
        ): ResponseEntity<CommonResponse<T>> {
            val response = CommonResponse(result = false, code = code, data = data)
            return status(status).body(response)
        }

        fun successCreate(): ResponseEntity<CommonResponse<String>> {
            return success(data = "데이터가 저장되었습니다.")
        }

        fun successUpdate(): ResponseEntity<CommonResponse<String>> {
            return success(data = "데이터가 수정되었습니다.")
        }

        fun successDelete(): ResponseEntity<CommonResponse<String>> {
            return success(data = "데이터가 삭제되었습니다.")
        }
    }
}
