package com.playlist.domains.playlist.presentation

import com.playlist.commons.config.response.ApiResponse
import com.playlist.domains.playlist.application.PlaylistService
import com.playlist.domains.playlist.application.dto.CreatePlaylistRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/playlists")
class PlaylistController(
    private val playlistService: PlaylistService
) {
    @PostMapping
    fun postPlaylist(@RequestBody request: CreatePlaylistRequest): ResponseEntity<Any> {
        playlistService.createPlaylist(request)

        return ApiResponse.successCreate()
    }
}