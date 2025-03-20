package com.playlist.commons.config.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ApiResponse<T>(status: HttpStatus) : ResponseEntity<T>(status) {
    companion object {
        fun successCreate(): ResponseEntity<Any> {
            return ok("데이터가 저장되었습니다.")
        }

        fun successUpdate(): ResponseEntity<Any> {
            return ok("데이터가 수정되었습니다.")
        }

        fun successDelete(): ResponseEntity<Any> {
            return ok("데이터가 삭제되었습니다.")
        }
    }
}