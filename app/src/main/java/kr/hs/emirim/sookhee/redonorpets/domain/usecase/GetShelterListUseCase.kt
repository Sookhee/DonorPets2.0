package kr.hs.emirim.sookhee.redonorpets.domain.usecase

import kr.hs.emirim.sookhee.redonorpets.data.shelter.ShelterRepository
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter
import javax.inject.Inject

/**
 *  GetShelterListUseCase.kt
 *
 *  Created by Minji Jeong on 2022/01/26
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */
class GetShelterListUseCase @Inject constructor(
    private val shelterRepository: ShelterRepository
) {
    suspend operator fun invoke(): List<Shelter> {
        return shelterRepository.getShelterList()
    }
}
