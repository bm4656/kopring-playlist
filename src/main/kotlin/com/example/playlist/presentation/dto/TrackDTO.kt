package com.example.playlist.presentation.dto

import com.example.playlist.domain.entity.Track

class TrackDTO(
    val id: Long?,
    val title: String,
    val artist: String,
    val duration: String,
) {
    constructor(track: Track) : this(
        id = track.id,
        title = track.title,
        artist = track.artist,
        duration = track.duration,
    )
}