package kr.hs.emirim.sookhee.redonorpets.data.shelter

import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter
import javax.inject.Inject

/**
 *  ShelterRepository.kt
 *
 *  Created by Minji Jeong on 2022/01/26
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class ShelterRepository @Inject constructor(
    private val shelterDataSource: ShelterDataSource
) {
    suspend fun getShelterList(): List<Shelter> {
        val response = shelterDataSource.getShelterList()

        val shelterList: MutableList<Shelter> = mutableListOf()
        response.forEach {
            val storyCount = shelterDataSource.getShelterStoryCount(it.id)

            val shelterItem =
                ShelterMapper.mapToEntity(it, storyCount)

            shelterList.add(shelterItem)
        }

        return shelterList
    }
}
