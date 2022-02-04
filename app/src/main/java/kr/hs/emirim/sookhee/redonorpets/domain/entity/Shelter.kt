package kr.hs.emirim.sookhee.redonorpets.domain.entity

/**
 *  Shelter.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

data class Shelter(
    val id: String = "",
    val name: String = "",
    val area: String = "",
    val phone: String = "",
    val profileImage: String = "",
    val storyCount: Int = 0,
    val donorCount: Int = 0,
    val likeCount: Int = 0,
    val objectList: List<Donation> = emptyList()
)
