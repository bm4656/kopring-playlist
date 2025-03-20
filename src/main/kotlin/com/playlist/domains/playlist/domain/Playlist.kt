package com.playlist.domains.playlist.domain

import com.playlist.commons.config.datetime.BaseEntity
import com.playlist.domains.member.domain.Member
import com.playlist.domains.track_playlist.domain.TrackPlaylist
import jakarta.persistence.*

@Entity
class Playlist(
    member: Member,
    title: String,
    playlistImage: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    var id: Long? = null

    @ManyToOne(targetEntity = Member::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var host: Member = member

    var title: String = title

    var playlistImage: String = playlistImage

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var trackPlaylists: MutableList<TrackPlaylist> = mutableListOf()

    fun addTrackPlaylist(trackPlaylist: TrackPlaylist) {
        this.trackPlaylists.add(trackPlaylist)
        trackPlaylist.playlist = this
    }

    fun countTrackPlaylists(): Int {
        return this.trackPlaylists.size
    }
}