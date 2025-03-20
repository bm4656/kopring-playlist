package com.playlist.domains.playlist.application.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PlaylistRequest(
    @field:NotBlank(message = "제목은 필수값입니다.")
    val title: String,

    @field:NotNull(message = "hostId는 필수값입니다.")
    @field:Positive(message = "hostId는 양수여야 합니다.")
    val hostId: Long,

    val playlistImage: String,

    @field:NotEmpty(message = "트랙 리스트는 최소 한 개 이상이어야 합니다.")
    val trackIds: List<@Positive(message = "트랙 ID는 양수여야 합니다.") Long>
)
