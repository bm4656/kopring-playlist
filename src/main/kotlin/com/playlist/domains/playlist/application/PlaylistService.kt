package com.playlist.domains.playlist.application

import com.playlist.commons.exception.BadRequestCustomException
import com.playlist.domains.member.domain.MemberRepository
import com.playlist.domains.playlist.application.dto.PlaylistRequest
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
    @Transactional(readOnly = true)
    fun getPlaylists(): List<Playlist> {
        return playlistRepository.findAllWithDetails()
    }

    @Transactional
    fun getPlaylistsByUserId(userId: Long): List<Playlist> {
        val member = memberRepository.findById(userId)
            .orElseThrow { throw BadRequestCustomException("해당 멤버가 존재하지 않습니다.") }

        return playlistRepository.findByHostWithDetails(member)
    }

    @Transactional
    fun createPlaylist(request: PlaylistRequest): Playlist {
        val host = memberRepository.findById(request.hostId)
            .orElseThrow { IllegalArgumentException("해당 멤버가 존재하지 않습니다.") }

        val playlist = Playlist(
            member = host,
            title = request.title,
            playlistImage = request.playlistImage

        )
        playlistRepository.save(playlist)

        this.addTrackPlaylists(request, playlist)

        return playlist
    }

    @Transactional(readOnly = true)
    fun getPlaylist(playlistId: Long): Playlist {
        return playlistRepository.findByIdWithDetails(playlistId)
            .orElseThrow { throw BadRequestCustomException("해당 플레이리스트가 존재하지 않습니다.") }
    }

    @Transactional
    fun updatePlaylist(id: Long, request: PlaylistRequest) {
        val playlist = playlistRepository.findById(id)
            .orElseThrow { throw BadRequestCustomException("해당 플레이리스트가 존재하지 않습니다.") }

        if (playlist.host.id != request.hostId) {
            throw BadRequestCustomException("해당 플레이리스트의 호스트가 아닙니다.")
        }

        playlist.title = request.title
        playlist.playlistImage = request.playlistImage
        this.updateTrackPlaylists(playlist, request.trackIds)

        playlistRepository.save(playlist)
    }

    @Transactional
    fun deletePlaylist(id: Long) {
        val playlist = playlistRepository.findById(id)
            .orElseThrow { throw BadRequestCustomException("해당 플레이리스트가 존재하지 않습니다.") }

        playlistRepository.delete(playlist)
    }

    private fun addTrackPlaylists(request: PlaylistRequest, playlist: Playlist) {
        // TODO TrackPlaylistRepository를 사용할지 고민 필요(서비스 분리)
        request.trackIds.forEach { trackId ->
            val track = trackRepository.findById(trackId)
                .orElseThrow { throw BadRequestCustomException("해당 음원 트랙이 존재하지 않습니다.") }

            val trackPlaylist = TrackPlaylist(
                track = track,
                playlist = playlist
            )

            playlist.addTrackPlaylist(trackPlaylist)
        }
    }

    private fun updateTrackPlaylists(playlist: Playlist, trackIds: List<Long>) {
        val currentTrackIds = playlist.trackPlaylists.map { it.track.id!! }.toSet()

        val trackPlaylistsToRemove = playlist.trackPlaylists.filter { it.track.id !in trackIds }
        playlist.trackPlaylists.removeAll(trackPlaylistsToRemove)

        val trackIdsToAdd = trackIds.filterNot { it in currentTrackIds }
        trackIdsToAdd.forEach { trackId ->
            val track = trackRepository.findById(trackId)
                .orElseThrow { BadRequestCustomException("해당 음원 트랙이 존재하지 않습니다.") }

            val trackPlaylist = TrackPlaylist(track = track, playlist = playlist)
            playlist.addTrackPlaylist(trackPlaylist)
        }
    }
}