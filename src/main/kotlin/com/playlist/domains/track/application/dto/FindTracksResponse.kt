package com.playlist.domains.track.application.dto

import com.playlist.domains.track.domain.Track

class FindTracksResponse(
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