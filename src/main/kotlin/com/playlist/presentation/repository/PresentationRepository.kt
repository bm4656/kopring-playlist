package com.playlist.presentation.repository

import com.playlist.domain.entity.Track
import org.springframework.stereotype.Repository

@Repository
class PresentationRepository(
    private val trackRepository: com.playlist.domain.repository.TrackRepository,
) {

    fun getTracks(): List<Track> {
        return trackRepository.findAll()
    }

}