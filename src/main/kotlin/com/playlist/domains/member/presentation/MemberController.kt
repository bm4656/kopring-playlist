package com.playlist.domains.member.presentation

import com.playlist.commons.config.response.ApiResponse
import com.playlist.commons.config.response.ApiResponseEntity
import com.playlist.domains.member.application.MemberService
import com.playlist.domains.member.application.dto.LoginMemberResponse
import com.playlist.domains.member.application.dto.MemberRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/register")
    fun createMember(@RequestBody request: MemberRequest): ApiResponseEntity<Map<String, String>> {
        memberService.createMember(request)
        return ApiResponse.successCreate("회원가입이 완료되었습니다.")
    }

    @PostMapping("/login")
    fun loginMember(@RequestBody request: MemberRequest): ApiResponseEntity<Map<String, LoginMemberResponse>> {
        val member = memberService.loginMember(request)
        return ApiResponse.success(data = mapOf("member" to LoginMemberResponse(member)))
    }
}


