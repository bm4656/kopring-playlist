package com.playlist.domains.track.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TrackRepository : JpaRepository<Track, Long>