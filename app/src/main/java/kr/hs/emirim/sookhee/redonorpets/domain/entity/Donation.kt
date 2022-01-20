package kr.hs.emirim.sookhee.redonorpets.domain.entity

/**
 *  Donation.kt
 *
 *  Created by Minji Jeong on 2022/01/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

data class Donation(
    val id: String,
    val name: String = "",
    val point: Int = 0,
    val imageUri: String = "",
    val quantity: Int = 1,
    var isChecked: Boolean = false,
)
