package com.example.playlist.domain.repository

import com.example.playlist.domain.entity.Track
import org.springframework.data.jpa.repository.JpaRepository

interface TrackRepository: JpaRepository<Track, Long> {
}