package com.playlist.domain.repository

import com.playlist.domain.entity.Playlist
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistRepository :JpaRepository<com.playlist.domain.entity.Playlist, Long> {
}