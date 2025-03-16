package com.playlist.domain

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.playlist.domain.entity.Member
import com.playlist.domain.entity.Track
import com.playlist.domain.repository.PlaylistRepository
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component()
@Profile(value = ["default"])
class DataInitializer(
    private val memberRepository: com.playlist.domain.repository.MemberRepository,
    private val trackRepository: com.playlist.domain.repository.TrackRepository,
    private val playlistRepository: PlaylistRepository,
    private val trackPlaylistRepository: com.playlist.domain.repository.TrackPlaylistRepository
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