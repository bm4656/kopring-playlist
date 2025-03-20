package com.playlist.domains.track.presentation

import com.playlist.commons.config.response.ApiResponse
import com.playlist.commons.config.response.CommonResponse
import com.playlist.domains.track.application.TrackService
import com.playlist.domains.track.application.dto.FindTracksResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tracks")
class TrackController(private val trackService: TrackService) {
    @GetMapping()
    fun getTracks(): ResponseEntity<CommonResponse<Map<String, List<FindTracksResponse>>>> {
        val tracks = trackService.getTracks()
        return ApiResponse.success(data = mapOf("tracks" to tracks))
    }
}