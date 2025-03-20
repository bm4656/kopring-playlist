package com.playlist.presentation.service

import com.playlist.domains.track.domain.Track
import com.playlist.presentation.repository.PresentationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class PresentationServiceTest {
    @InjectMocks
    lateinit var presentationService: PresentationService

    @Mock
    lateinit var presentationRepository: PresentationRepository

    val DATA_SIZE = 5
    val tracks = mutableListOf<Track>()

    @BeforeEach
    fun setup() {
        tracks.clear()
        for (i in 1..DATA_SIZE) {
            val track = Track(title = "Title-$i", artist = "Artist-$i", duration = "03:0$i")
            tracks.add(track)
        }
    }


    @Test
    @DisplayName("음원 전체 목록이 조회된다.")
    fun testGetTracks() {
        // given
        Mockito.`when`(presentationRepository.getTracks()).thenReturn(tracks)

        // when
        val result = presentationService.getTracks()

        // then
        assertThat(result).hasSize(DATA_SIZE)
        for (i in 0 until DATA_SIZE) {
            assertThat(result[i].title).isEqualTo(tracks[i].title)
            assertThat(result[i].artist).isEqualTo(tracks[i].artist)
            assertThat(result[i].duration).isEqualTo(tracks[i].duration)
        }
    }

    @Test
    @DisplayName("음원 없다면 빈 배열을 반환한다.")
    fun testGetTracksEmpty() {
        // given
        Mockito.`when`(presentationRepository.getTracks()).thenReturn(emptyList())

        // when
        val result = presentationRepository.getTracks()

        // then
        assertThat(result).isEmpty()
    }


    @Test
    @DisplayName("음원 조회 중 예외가 발생하면 처리된다.")
    fun testGetTracksExceptionHandling() {
        // given
        Mockito.`when`(presentationRepository.getTracks()).thenThrow(RuntimeException("조회 중 오류가 발생했습니다."))

        // when & then
        assertThrows(RuntimeException::class.java) {
            presentationService.getTracks()
        }
    }
}