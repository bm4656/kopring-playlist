package com.example.playlist.domain.entity

import jakarta.persistence.*

@Entity
class TrackPlaylist(
    track: Track,
    playlist: Playlist,
):BaseEntity() {
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