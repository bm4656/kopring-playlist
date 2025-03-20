package com.playlist.domains.playlist.application.dto

import com.playlist.domains.track.domain.Track

data class FindPlaylistResponse(
    val title: String,
    val hostName: String,
    val playlistImage: String,
    val tracks: List<Track>,
)
