package kr.hs.emirim.sookhee.redonorpets.domain.entity

/**
 *  Badge.kt
 *
 *  Created by Minji Jeong on 2022/01/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

data class Badge(
    val id: String,
    val isClear: Boolean = false,
    val title: String = "",
    val imageUrl: String = ""
)
