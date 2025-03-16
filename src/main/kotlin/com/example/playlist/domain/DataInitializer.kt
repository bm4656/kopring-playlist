package com.example.playlist.domain

import com.example.playlist.domain.entity.Member
import com.example.playlist.domain.entity.Playlist
import com.example.playlist.domain.entity.Track
import com.example.playlist.domain.repository.MemberRepository
import com.example.playlist.domain.repository.PlaylistRepository
import com.example.playlist.domain.repository.TrackPlaylistRepository
import com.example.playlist.domain.repository.TrackRepository
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
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

    }
}