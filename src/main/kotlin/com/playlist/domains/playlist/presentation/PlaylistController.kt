package com.playlist.domains.playlist.presentation

import com.playlist.commons.config.response.ApiResponse
import com.playlist.commons.config.response.ApiResponseEntity
import com.playlist.commons.config.response.CommonResponse
import com.playlist.domains.playlist.application.PlaylistService
import com.playlist.domains.playlist.application.dto.FindPlaylistResponse
import com.playlist.domains.playlist.application.dto.FindPlaylistsResponse
import com.playlist.domains.playlist.application.dto.PlaylistRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/playlists")
class PlaylistController(
    private val playlistService: PlaylistService
) {
    @GetMapping
    fun getPlaylists(): ApiResponseEntity<Map<String, List<FindPlaylistsResponse>>> {
        val playlists = playlistService.getPlaylists()
        val response = playlists.map {
            FindPlaylistsResponse(it)
        }

        return ApiResponse.success(
            data = mapOf("playlists" to response)
        )
    }

    @GetMapping("/users/{userId}")
    fun getPlaylistsByUser(@PathVariable userId: Long): ApiResponseEntity<Map<String, List<FindPlaylistsResponse>>> {
        val playlists = playlistService.getPlaylistsByUserId(userId)
        val response = playlists.map {
            FindPlaylistsResponse(it)
        }

        return ApiResponse.success(
            data = mapOf("playlists" to response)
        )
    }


    @PostMapping
    fun postPlaylist(@RequestBody @Valid request: PlaylistRequest): ResponseEntity<CommonResponse<Map<String, String>>> {
        playlistService.createPlaylist(request)
        return ApiResponse.successCreate("플레이리스트가 생성되었습니다.")
    }

    @GetMapping("/{id}")
    fun getPlaylist(@PathVariable id: Long): ApiResponseEntity<Map<String, FindPlaylistResponse>> {
        val playlist = playlistService.getPlaylist(id)
        val response = FindPlaylistResponse(
            title = playlist.title,
            hostName = playlist.host.username,
            playlistImage = playlist.playlistImage,
            tracks = playlist.trackPlaylists.map { it.track }
        )

        return ApiResponse.success(
            data = mapOf("playlist" to response)
        )
    }

    @PatchMapping("/{id}")
    fun updatePlaylist(
        @PathVariable id: Long,
        @RequestBody @Valid request: PlaylistRequest
    ): ApiResponseEntity<Map<String, String>> {
        playlistService.updatePlaylist(id, request)
        return ApiResponse.successUpdate("플레이리스트가 수정되었습니다.")
    }

    @DeleteMapping("/{id}")
    fun deletePlaylist(@PathVariable id: Long): ApiResponseEntity<Map<String, String>> {
        playlistService.deletePlaylist(id)
        return ApiResponse.successDelete("플레이리스트가 삭제되었습니다.")
    }
}