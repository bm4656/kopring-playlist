package com.example.playlist.domain.entity

import jakarta.persistence.*

@Entity
class Playlist(
    member: Member,
    title: String,
    playlistImage: String,
):BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    var id: Long? = null

    @ManyToOne(targetEntity = Member::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var host : Member = member;

    var title: String = title

    var playlistImage: String = playlistImage

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY)
    var trackPlaylists: MutableList<TrackPlaylist> = mutableListOf()
}