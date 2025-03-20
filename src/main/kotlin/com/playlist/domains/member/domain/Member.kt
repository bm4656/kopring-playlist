package com.playlist.domains.member.domain

import com.playlist.commons.config.datetime.BaseEntity
import com.playlist.commons.constant.YesNo
import jakarta.persistence.*

@Entity
class Member(
    username: String,
    password: String,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null

    var username: String = username

    var password: String = password

    var status: YesNo = YesNo.Y

}
