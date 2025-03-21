package com.playlist.domains.playlist.application

import com.playlist.commons.exception.BadRequestCustomException
import com.playlist.domains.member.domain.Member
import com.playlist.domains.member.domain.MemberRepository
import com.playlist.domains.playlist.application.dto.PlaylistRequest
import com.playlist.domains.playlist.domain.Playlist
import com.playlist.domains.playlist.domain.PlaylistRepository
import com.playlist.domains.track.domain.Track
import com.playlist.domains.track.domain.TrackRepository
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
import java.util.*

@ExtendWith(MockitoExtension::class)
class PlaylistServiceTest {
    @InjectMocks
    lateinit var playlistService: PlaylistService

    @Mock
    lateinit var playlistRepository: PlaylistRepository

    @Mock
    lateinit var memberRepository: MemberRepository

    @Mock
    lateinit var trackRepository: TrackRepository

    private lateinit var member: Member
    private lateinit var otherMember: Member
    private lateinit var playlist: Playlist
    private lateinit var track1: Track
    private lateinit var track2: Track

    @BeforeEach
    fun setup() {
        member = Member(username = "testUser", password = "1234")
        otherMember = Member(username = "otherUser", password = "password123")

        Mockito.`when`(memberRepository.save(Mockito.any(Member::class.java)))
            .thenAnswer { invocation ->
                val savedMember = invocation.getArgument<Member>(0)
                savedMember.id = if (savedMember.username == "testUser") 1 else 2
                savedMember
            }

        member = memberRepository.save(member)
        otherMember = memberRepository.save(otherMember)

        track1 = Track(title = "Track 1", artist = "Artist 1", duration = "03:30")
        track2 = Track(title = "Track 2", artist = "Artist 2", duration = "04:15")

        playlist = Playlist(member = member, title = "Old Title", playlistImage = "https://image.url")
    }

    @Test
    @DisplayName("플레이리스트가 생성된다.")
    fun testCreatePlaylistSuccess() {
        // given
        val request = PlaylistRequest(
            title = "My Playlist",
            hostId = 1,
            playlistImage = "https://image.url",
            trackIds = listOf(1, 2)
        )

        val playlist = Playlist(member = member, title = request.title, playlistImage = request.playlistImage)

        Mockito.`when`(memberRepository.findById(1)).thenReturn(Optional.of(member))
        Mockito.`when`(trackRepository.findById(1)).thenReturn(Optional.of(track1))
        Mockito.`when`(trackRepository.findById(2)).thenReturn(Optional.of(track2))
        Mockito.`when`(playlistRepository.save(Mockito.any(Playlist::class.java))).thenReturn(playlist)

        // when
        val result = playlistService.createPlaylist(request)

        // then
        assertThat(result.title).isEqualTo(request.title)
        assertThat(result.host).isEqualTo(member)
        assertThat(result.playlistImage).isEqualTo(request.playlistImage)
    }

    @Test
    @DisplayName("존재하지 않는 멤버로는 플레이리스트 생성되지 않는다.")
    fun testCreatePlaylistFailWhenMemberNotFound() {
        // given
        val request = PlaylistRequest(
            title = "My Playlist",
            hostId = 10,
            playlistImage = "https://image.url",
            trackIds = listOf(1, 2)
        )

        Mockito.`when`(memberRepository.findById(10)).thenReturn(Optional.empty())

        // when & then
        assertThrows(IllegalArgumentException::class.java) {
            playlistService.createPlaylist(request)
        }
    }

    @Test
    @DisplayName("트랙이 존재하지 않는 경우 플레이리스트는 생성되지 않는다.")
    fun testCreatePlaylistFailWhenTrackNotFound() {
        // given
        val request = PlaylistRequest(
            title = "My Playlist",
            hostId = 1,
            playlistImage = "https://image.url",
            trackIds = listOf(1, 10)
        )

        Mockito.`when`(memberRepository.findById(1)).thenReturn(Optional.of(member))
        Mockito.`when`(trackRepository.findById(1)).thenReturn(Optional.of(track1))
        Mockito.`when`(trackRepository.findById(10)).thenReturn(Optional.empty()) // 존재하지 않는 트랙

        // when & then
        assertThrows(BadRequestCustomException::class.java) {
            playlistService.createPlaylist(request)
        }
    }

    @Test
    @DisplayName("플레이리스트를 생성한 사람은 플레이리스트를 수정할 수 있다.")
    fun testUpdatePlaylistSuccess() {
        // given
        playlist.id = 1L

        val request = PlaylistRequest(
            title = "Updated Title",
            hostId = 1L,
            playlistImage = "https://new.image.url",
            trackIds = listOf(1L)
        )

        Mockito.`when`(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist))
        Mockito.`when`(trackRepository.findById(1L)).thenReturn(Optional.of(track1))

        // when
        playlistService.updatePlaylist(1L, request)

        // then
        assertThat(playlist.title).isEqualTo("Updated Title")
        assertThat(playlist.playlistImage).isEqualTo("https://new.image.url")
    }

    @Test
    @DisplayName("플레이리스트가 존재하지 않는다면 수정에 실패한다.")
    fun testUpdatePlaylistFailWhenNotFound() {
        // given
        val request = PlaylistRequest(
            title = "Updated Title",
            hostId = 1,
            playlistImage = "https://new.image.url",
            trackIds = listOf()
        )

        Mockito.`when`(playlistRepository.findById(10)).thenReturn(Optional.empty())

        // when & then
        assertThrows(BadRequestCustomException::class.java) {
            playlistService.updatePlaylist(10, request)
        }
    }

    @Test
    @DisplayName("다른 사용자가 타인의 플레이리스트를 수정할 수 없다.")
    fun testUpdatePlaylistFailWhenNotHost() {
        // given
        val request = PlaylistRequest(
            title = "Updated Title",
            hostId = 10, // 다른 사용자 ID
            playlistImage = "https://new.image.url",
            trackIds = listOf()
        )

        Mockito.`when`(playlistRepository.findById(1)).thenReturn(Optional.of(playlist))

        // when & then
        assertThrows(BadRequestCustomException::class.java) {
            playlistService.updatePlaylist(1, request)
        }
    }

    @Test
    @DisplayName("플레이리스트를 만든 사람은 삭제할 수 있다.")
    fun testDeletePlaylistSuccess() {
        // given
        Mockito.`when`(playlistRepository.findById(1)).thenReturn(Optional.of(playlist))

        // when
        playlistService.deletePlaylist(1)

        // then
        Mockito.verify(playlistRepository, Mockito.times(1)).delete(playlist)
    }

    @Test
    @DisplayName("플레이리스트가 존재하지 않을 때 삭제에 실패한다.")
    fun testDeletePlaylistFailWhenNotFound() {
        // given
        Mockito.`when`(playlistRepository.findById(10)).thenReturn(Optional.empty())

        // when & then
        assertThrows(BadRequestCustomException::class.java) {
            playlistService.deletePlaylist(10)
        }
    }

    @Test
    @DisplayName("이미 삭제된 플레이리스트는 다시 삭제할 수 없다.")
    fun testDeletePlaylistFailWhenAlreadyDeleted() {
        // given
        Mockito.`when`(playlistRepository.findById(1)).thenReturn(Optional.empty()) // 이미 삭제된 상태

        // when & then
        assertThrows(BadRequestCustomException::class.java) {
            playlistService.deletePlaylist(1)
        }
    }

//    @Test TODO 해당 케이스 서비스에 반영 필요
//    @DisplayName("다른 사용자의 플레이리스트를 삭제할 수 없다.")
//    fun testDeletePlaylistFailWhenNotHost() {
//        // given
//        val otherUserPlaylist =
//            Playlist(member = otherMember, title = "Other Playlist", playlistImage = "https://image.url")
//        Mockito.`when`(playlistRepository.findById(1)).thenReturn(Optional.of(otherUserPlaylist))
//
//        // when & then
//        assertThrows(BadRequestCustomException::class.java) {
//            playlistService.deletePlaylist(1)
//        }
//    }
}