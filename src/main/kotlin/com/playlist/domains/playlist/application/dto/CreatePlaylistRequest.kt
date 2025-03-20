package com.playlist.domains.playlist.application.dto

data class CreatePlaylistRequest(
    val title: String,
    val hostId: Long,
    val playlistImage: String,
    val trackIds: List<Long>
)
