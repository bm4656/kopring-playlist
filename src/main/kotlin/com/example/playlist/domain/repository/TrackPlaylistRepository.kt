package com.example.playlist.domain.repository

import com.example.playlist.domain.entity.TrackPlaylist
import org.springframework.data.jpa.repository.JpaRepository

interface TrackPlaylistRepository: JpaRepository<TrackPlaylist, Long> {
}