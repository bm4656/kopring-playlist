package com.example.playlist.presentation.service

import com.example.playlist.presentation.dto.TrackDTO
import com.example.playlist.presentation.repository.PresentationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PresentationService(
    private val presentationRepository: PresentationRepository,
) {

    @Transactional(readOnly = true)
    fun getTracks(): List<TrackDTO> {
        val tracks = presentationRepository.getTracks()

        return tracks.map { TrackDTO(it) }
    }

}