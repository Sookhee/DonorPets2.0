package kr.hs.emirim.sookhee.redonorpets.domain.entity

/**
 *  Story.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

data class Story(
    val id: String = "",
    val title: String = "",
    val createDate: String = "",
    val updateDate: String = "",
    val content: List<String> = emptyList(),
    val shelterId: String = "",
    val shelterName: String = "",
    val shelterProfile: String = "",
    val likeCount: Int = 0,
)
