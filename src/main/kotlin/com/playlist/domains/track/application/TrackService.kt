package com.playlist.domains.track.application

import com.playlist.domains.track.application.dto.FindTracksResponse
import com.playlist.domains.track.domain.TrackRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TrackService(
    private val trackRepository: TrackRepository
) {
    @Transactional(readOnly = true)
    fun getTracks(): List<FindTracksResponse> {
        val tracks = trackRepository.findAll()

        return tracks.map { FindTracksResponse(it) }
    }
}