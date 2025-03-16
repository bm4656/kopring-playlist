package com.playlist.domain.entity

import jakarta.persistence.*

@Entity
class TrackPlaylist(
    track: com.playlist.domain.entity.Track,
    playlist: com.playlist.domain.entity.Playlist,
): com.playlist.domain.entity.BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_playlist_id")
    var id: Long? = null

    @ManyToOne(targetEntity = com.playlist.domain.entity.Playlist::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    var playlist: com.playlist.domain.entity.Playlist = playlist

    @ManyToOne(targetEntity = com.playlist.domain.entity.Track::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    var track: com.playlist.domain.entity.Track = track
}