package com.playlist.domains.member.application.dto

import com.playlist.domains.member.domain.Member

data class LoginMemberResponse(
    val id: Long?,
    val username: String
) {
    constructor(member: Member) : this(
        id = member.id,
        username = member.username
    )
}
