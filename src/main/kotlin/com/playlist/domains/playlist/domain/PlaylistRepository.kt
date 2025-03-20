package com.playlist.domains.playlist.domain

import com.playlist.domains.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PlaylistRepository : JpaRepository<Playlist, Long> {

    @Query("select p from Playlist p join fetch p.host left join fetch p.trackPlaylists tp left join fetch tp.track where p.id = :id")
    fun findByIdWithDetails(id: Long): Optional<Playlist>

    @Query("select p from Playlist p join fetch p.host left join fetch p.trackPlaylists tp left join fetch tp.track")
    fun findAllWithDetails(): List<Playlist>

    @Query("select p from Playlist p join fetch p.host left join fetch p.trackPlaylists tp left join fetch tp.track where p.host = :host")
    fun findByHostWithDetails(host: Member): List<Playlist>
}