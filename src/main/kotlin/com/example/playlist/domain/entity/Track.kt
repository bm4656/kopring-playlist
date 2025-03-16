package com.example.playlist.domain.entity

import jakarta.persistence.*

@Entity
class Track(
    title: String,
    artist: String,
    duration: String,
):BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    var id: Long? = null

    var title: String = title

    var artist: String = artist

    var duration: String = duration
}