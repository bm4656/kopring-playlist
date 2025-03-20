package com.playlist.domains.track_playlist.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TrackPlaylistRepository : JpaRepository<TrackPlaylist, Long>