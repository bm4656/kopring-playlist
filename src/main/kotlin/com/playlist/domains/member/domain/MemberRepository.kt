package com.playlist.domains.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUsernameAndPassword(username: String, password: String): Member?
}