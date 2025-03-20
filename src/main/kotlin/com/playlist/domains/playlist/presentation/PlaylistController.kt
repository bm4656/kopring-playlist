package com.playlist.domains.playlist.presentation

import com.playlist.commons.config.response.ApiResponse
import com.playlist.commons.config.response.CommonResponse
import com.playlist.domains.playlist.application.PlaylistService
import com.playlist.domains.playlist.application.dto.CreatePlaylistRequest
import com.playlist.domains.playlist.application.dto.FindPlaylistResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/playlists")
class PlaylistController(
    private val playlistService: PlaylistService
) {
    @PostMapping
    fun postPlaylist(@RequestBody request: CreatePlaylistRequest): ResponseEntity<CommonResponse<String>> {
        playlistService.createPlaylist(request)

        return ApiResponse.successCreate()
    }

    @GetMapping("/{id}")
    fun getPlaylist(@PathVariable id: Long): ResponseEntity<CommonResponse<FindPlaylistResponse>> {
        val playlist = playlistService.getPlaylist(id)

        return ApiResponse.success(
            data = FindPlaylistResponse(
                title = playlist.title,
                hostName = playlist.host.username,
                playlistImage = playlist.playlistImage,
                tracks = playlist.trackPlaylists.map { it.track }
            ))
    }
}