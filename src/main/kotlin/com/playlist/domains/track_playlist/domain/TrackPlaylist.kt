package com.playlist.domains.track_playlist.domain

import com.playlist.commons.config.datetime.BaseEntity
import com.playlist.domains.playlist.domain.Playlist
import com.playlist.domains.track.domain.Track
import jakarta.persistence.*

@Entity
class TrackPlaylist(
    track: Track,
    playlist: Playlist,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_playlist_id")
    var id: Long? = null

    @ManyToOne(targetEntity = Playlist::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    var playlist: Playlist = playlist

    @ManyToOne(targetEntity = Track::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    var track: Track = track
}