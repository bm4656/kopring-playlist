package com.example.playlist.domain.repository

import com.example.playlist.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository:JpaRepository<Member, Long> {
}