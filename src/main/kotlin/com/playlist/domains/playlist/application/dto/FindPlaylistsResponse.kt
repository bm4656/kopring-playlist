package com.playlist.domains.playlist.application.dto

import com.playlist.domains.playlist.domain.Playlist

data class FindPlaylistsResponse(
    val id: Long?,
    val title: String,
    val hostName: String,
    val playlistImage: String,
    val trackCount: Int,
) {
    constructor(playlist: Playlist) : this(
        id = playlist.id,
        title = playlist.title,
        hostName = playlist.host.username,
        playlistImage = playlist.playlistImage,
        trackCount = playlist.countTrackPlaylists()
    )
}