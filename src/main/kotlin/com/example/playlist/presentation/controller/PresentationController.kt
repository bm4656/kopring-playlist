package com.example.playlist.presentation.controller

import com.example.playlist.presentation.dto.TrackDTO
import com.example.playlist.presentation.repository.PresentationRepository
import com.example.playlist.presentation.service.PresentationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PresentationController(
    private val presentationService: PresentationService
) {

    @GetMapping("/test")
    fun test(): String {
        return "ok"
    }

    @GetMapping("/tracks")
    fun getTracks(): List<TrackDTO> {
        return presentationService.getTracks()
    }
}