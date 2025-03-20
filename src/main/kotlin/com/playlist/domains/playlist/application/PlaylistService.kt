package com.playlist.domains.playlist.application

import com.playlist.commons.exception.BadRequestCustomException
import com.playlist.domains.member.domain.MemberRepository
import com.playlist.domains.playlist.application.dto.CreatePlaylistRequest
import com.playlist.domains.playlist.domain.Playlist
import com.playlist.domains.playlist.domain.PlaylistRepository
import org.springframework.stereotype.Service

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
    private val memberRepository: MemberRepository
) {
    fun createPlaylist(request: CreatePlaylistRequest): Playlist {
        val host = memberRepository.findById(request.hostId)
            .orElseThrow { IllegalArgumentException("해당 멤버가 존재하지 않습니다.") }

        val playlist = Playlist(
            member = host,
            title = request.title,
            playlistImage = request.playlistImage
        )

        return playlistRepository.save(playlist)
    }

    fun getPlaylist(playlistId: Long): Playlist {
        return playlistRepository.findById(playlistId)
            .orElseThrow { throw BadRequestCustomException("해당 플레이리스트가 존재하지 않습니다.") }
    }
}