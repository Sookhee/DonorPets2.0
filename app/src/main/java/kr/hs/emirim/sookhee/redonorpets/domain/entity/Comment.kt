package kr.hs.emirim.sookhee.redonorpets.domain.entity

/**
 *  Comment.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

data class Comment(
    val id: String = "",
    val name: String = "",
    val profileImage: String = "",
    val comment: String = ""
)