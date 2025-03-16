package com.example.playlist.presentation.repository

import com.example.playlist.domain.entity.Track
import com.example.playlist.domain.repository.TrackRepository
import org.springframework.stereotype.Repository

@Repository
class PresentationRepository(
    private val trackRepository: TrackRepository,
) {

    fun getTracks():List<Track> {
        return trackRepository.findAll()
    }

}