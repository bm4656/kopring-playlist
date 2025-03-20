package com.playlist.domains.playlist.application

import com.playlist.commons.exception.BadRequestCustomException
import com.playlist.domains.member.domain.MemberRepository
import com.playlist.domains.playlist.application.dto.CreatePlaylistRequest
import com.playlist.domains.playlist.domain.Playlist
import com.playlist.domains.playlist.domain.PlaylistRepository
import com.playlist.domains.track.domain.TrackRepository
import com.playlist.domains.track_playlist.domain.TrackPlaylist
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
    private val memberRepository: MemberRepository,
    private val trackRepository: TrackRepository
) {
    @Transactional
    fun createPlaylist(request: CreatePlaylistRequest): Playlist {
        val host = memberRepository.findById(request.hostId)
            .orElseThrow { IllegalArgumentException("해당 멤버가 존재하지 않습니다.") }

        val playlist = Playlist(
            member = host,
            title = request.title,
            playlistImage = request.playlistImage

        )
        playlistRepository.save(playlist)

        request.trackIds.forEach { trackId ->
            val track = trackRepository.findById(trackId)
                .orElseThrow { throw BadRequestCustomException("해당 음원 트랙이 존재하지 않습니다.") }

            val trackPlaylist = TrackPlaylist(
                track = track,
                playlist = playlist
            )

            playlist.addTrackPlaylist(trackPlaylist)
        }

        return playlist
    }

    @Transactional(readOnly = true)
    fun getPlaylist(playlistId: Long): Playlist {
        return playlistRepository.findByIdWithDetails(playlistId)
            .orElseThrow { throw BadRequestCustomException("해당 플레이리스트가 존재하지 않습니다.") }
    }
}