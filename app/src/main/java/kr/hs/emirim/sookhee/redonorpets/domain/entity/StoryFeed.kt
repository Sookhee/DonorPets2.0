package kr.hs.emirim.sookhee.redonorpets.domain.entity

/**
 *  StoryFeed.kt
 *
 *  Created by Minji Jeong on 2022/01/06
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

data class StoryFeed(
    val id: String? = null,
    val title: String = "",
    val shelterId: String = "",
    val shelterName: String = "",
    val shelterArea: String = "",
    val thumbnail: String = "",
    val date: String,
    val likeCount: Int = 0,
    val commentCount: Int = 0
)
