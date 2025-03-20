package com.playlist.domains.track.application

import com.playlist.domains.track.application.dto.TrackDTO
import com.playlist.domains.track.domain.TrackRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TrackService(
    private val trackRepository: TrackRepository
) {
    @Transactional(readOnly = true)
    fun getTracks(): List<TrackDTO> {
        val tracks = trackRepository.findAll()

        return tracks.map { TrackDTO(it) }
    }
}