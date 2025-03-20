package com.playlist.domains.track.presentation

import com.playlist.domains.track.application.TrackService
import com.playlist.domains.track.application.dto.TrackDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tracks")
class TrackController(private val trackService: TrackService) {
    @GetMapping()
    fun getTracks(): List<TrackDTO> {
        return trackService.getTracks()
    }
}