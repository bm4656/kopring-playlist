package com.playlist.presentation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PresentationController(
    private val presentationService: com.playlist.presentation.service.PresentationService
) {

    @GetMapping("/test")
    fun test(): String {
        return "ok"
    }

    @GetMapping("/tracks")
    fun getTracks(): List<com.playlist.presentation.dto.TrackDTO> {
        return presentationService.getTracks()
    }
}