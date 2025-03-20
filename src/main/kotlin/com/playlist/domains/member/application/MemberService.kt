package com.playlist.domains.member.application

import com.playlist.commons.exception.BadRequestCustomException
import com.playlist.domains.member.application.dto.MemberRequest
import com.playlist.domains.member.domain.Member
import com.playlist.domains.member.domain.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun createMember(request: MemberRequest): Member {
        val member = Member(
            username = request.username,
            password = request.password
        )

        return memberRepository.save(member)
    }

    fun loginMember(request: MemberRequest): Member {
        val member = memberRepository.findByUsernameAndPassword(request.username, request.password)
            ?: throw BadRequestCustomException("아이디 또는 비밀번호가 잘못되었습니다.")

        return member
    }
}