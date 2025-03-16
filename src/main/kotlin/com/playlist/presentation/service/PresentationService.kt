package com.playlist.presentation.service

import com.playlist.presentation.repository.PresentationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PresentationService(
    private val presentationRepository: PresentationRepository,
) {

    @Transactional(readOnly = true)
    fun getTracks(): List<com.playlist.presentation.dto.TrackDTO> {
        val tracks = presentationRepository.getTracks()

        return tracks.map { com.playlist.presentation.dto.TrackDTO(it) }
    }

}