package com.playlist.domain.repository

import com.playlist.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository:JpaRepository<com.playlist.domain.entity.Member, Long> {
}