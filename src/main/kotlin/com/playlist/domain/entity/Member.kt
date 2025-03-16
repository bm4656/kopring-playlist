package com.playlist.domain.entity

import com.playlist.domain.constants.YesNo
import jakarta.persistence.*

@Entity
class Member(
    username: String,
    password: String,
): com.playlist.domain.entity.BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null

    var username: String = username

    var password: String = password

    var status: com.playlist.domain.constants.YesNo = com.playlist.domain.constants.YesNo.Y

}
