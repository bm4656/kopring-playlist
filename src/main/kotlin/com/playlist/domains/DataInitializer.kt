package com.playlist.domains

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.playlist.domains.member.domain.Member
import com.playlist.domains.member.domain.MemberRepository
import com.playlist.domains.playlist.domain.Playlist
import com.playlist.domains.playlist.domain.PlaylistRepository
import com.playlist.domains.track.domain.Track
import com.playlist.domains.track.domain.TrackRepository
import com.playlist.domains.track_playlist.domain.TrackPlaylist
import com.playlist.domains.track_playlist.domain.TrackPlaylistRepository
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component()
@Profile(value = ["default"])
class DataInitializer(
    private val memberRepository: MemberRepository,
    private val trackRepository: TrackRepository,
    private val playlistRepository: PlaylistRepository,
    private val trackPlaylistRepository: TrackPlaylistRepository
) {
    @PostConstruct
    fun initializeData() {
        println("스프링이 실행되었습니다. 테스트 데이터를 초기화합니다.")

        // member 초기화
        val members = mutableListOf<Member>(
            Member(
                username = "test",
                password = "1234"
            ),
            Member(
                username = "bomin",
                password = "1234"
            )
        )
        memberRepository.saveAll(members)

        // track 초기화 - xml 파일로 데이터 추가
        val xmlMapper = XmlMapper()

        val inputStream = javaClass.getResourceAsStream("/track-data.xml")
            ?: throw IllegalStateException("track-data.xml not found")

        val tracks = xmlMapper.readValue<List<Track>>(inputStream)

        trackRepository.saveAll(tracks)

        // playlist 초기화
        val playlists = mutableListOf<Playlist>(
            Playlist(
                member = members[0],
                title = "플레이리스트 1",
                playlistImage = "https://www.google.com"
            ),
            Playlist(
                member = members[1],
                title = "플레이리스트 2",
                playlistImage = "https://www.google.com"
            ),
            Playlist(
                member = members[1],
                title = "플레이리스트 3",
                playlistImage = "https://www.google.com"
            ),
            Playlist(
                member = members[0],
                title = "플레이리스트 4",
                playlistImage = "https://www.google.com"
            ),
            Playlist(
                member = members[0],
                title = "플레이리스트 5",
                playlistImage = "https://www.google.com"
            ),
            Playlist(
                member = members[1],
                title = "플레이리스트 6",
                playlistImage = "https://www.google.com"
            )
        )
        playlistRepository.saveAll(playlists)

        // trackPlaylist 초기화
        val trackPlaylists = mutableListOf<TrackPlaylist>()

        val shuffledTracks = tracks.shuffled().toMutableList() // 트랙 섞기

        for (playlist in playlists) {
            if (shuffledTracks.isEmpty()) break

            // 각 플레이리스트에 랜덤한 수(1~10)만큼 트랙을 배정하되, 남은 트랙 수 고려
            val trackCount = (1..10).random().coerceAtMost(shuffledTracks.size)
            val assignedTracks = shuffledTracks.take(trackCount)

            assignedTracks.forEach { track ->
                val trackPlaylist = TrackPlaylist(track = track, playlist = playlist)
                playlist.addTrackPlaylist(trackPlaylist)
                trackPlaylists.add(trackPlaylist)
            }

            // 사용한 트랙 제거
            repeat(trackCount) { shuffledTracks.removeAt(0) }
        }

        trackPlaylistRepository.saveAll(trackPlaylists)
    }
}