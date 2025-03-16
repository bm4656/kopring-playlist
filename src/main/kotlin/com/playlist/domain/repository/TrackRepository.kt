package com.playlist.domain.repository

import com.playlist.domain.entity.Track
import org.springframework.data.jpa.repository.JpaRepository

interface TrackRepository : JpaRepository<Track, Long>