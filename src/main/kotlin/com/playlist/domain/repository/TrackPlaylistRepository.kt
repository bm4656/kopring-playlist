package com.playlist.domain.repository

import com.playlist.domain.entity.TrackPlaylist
import org.springframework.data.jpa.repository.JpaRepository

interface TrackPlaylistRepository: JpaRepository<com.playlist.domain.entity.TrackPlaylist, Long> {
}