package com.playlist.commons.exception

import org.springframework.http.HttpStatus

abstract class CustomException(
    httpStatus: HttpStatus,
    message: String
) : RuntimeException(message) {
    val httpStatus: HttpStatus = httpStatus
}

class BadRequestCustomException(
    message: String
) : CustomException(
    httpStatus = HttpStatus.BAD_REQUEST,
    message = message
)

class InternalServerErrorCustomException(message: String) : CustomException(
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    message = message
)