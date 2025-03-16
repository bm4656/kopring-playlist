package com.playlist.domain.entity

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import jakarta.persistence.*

@Entity
class Track(
    title: String,
    artist: String,
    duration: String,
    imageUrl: String? = null
): com.playlist.domain.entity.BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    var id: Long? = null

    var title: String = title

    var artist: String = artist

    var duration: String = duration

    @JacksonXmlProperty(localName = "image")
    var imageUrl: String? = null
}