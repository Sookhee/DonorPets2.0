package kr.hs.emirim.sookhee.redonorpets.data.shelter

import com.google.firebase.firestore.DocumentSnapshot
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter

/**
 *  ShelterMapper.kt
 *
 *  Created by Minji Jeong on 2022/01/26
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

object ShelterMapper {
    fun mapToEntity(
        shelter: DocumentSnapshot,
        storyCount: Int
    ): Shelter {
        return Shelter(
            id = shelter.id,
            name = shelter.getString("name") ?: "",
            area = shelter.getString("area") ?: "",
            phone = shelter.getString("phone") ?: "",
            profileImage = shelter.getString("profileImage") ?: "",
            storyCount = storyCount,
        )
    }
}
