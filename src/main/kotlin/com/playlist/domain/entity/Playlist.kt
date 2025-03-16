package com.playlist.domain.entity

import jakarta.persistence.*

@Entity
class Playlist(
    member: com.playlist.domain.entity.Member,
    title: String,
    playlistImage: String,
): com.playlist.domain.entity.BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    var id: Long? = null

    @ManyToOne(targetEntity = com.playlist.domain.entity.Member::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var host : com.playlist.domain.entity.Member = member;

    var title: String = title

    var playlistImage: String = playlistImage

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY)
    var trackPlaylists: MutableList<com.playlist.domain.entity.TrackPlaylist> = mutableListOf()
}