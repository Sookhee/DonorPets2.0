package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter
import kr.hs.emirim.sookhee.redonorpets.ui.UiState

/**
 *  ShelterViewModel.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class ShelterViewModel: ViewModel() {
    private val _shelterState = MutableStateFlow<UiState>(UiState.Loading)
    val shelterState: StateFlow<UiState> = _shelterState

    fun getShelterList() {
        _shelterState.value = UiState.Success(FAKE_SHELTER_LIST)
    }

    private val FAKE_SHELTER_LIST = listOf(
        Shelter(id = "", name = "도너츠 보호소", area = "서울", phone = "", profileImage = "", storyCount = 0),
        Shelter(id = "", name = "무지개 보호소", area = "경기도", phone = "", profileImage = "", storyCount = 0),
        Shelter(id = "", name = "호호 유기견 보호소", area = "서울", phone = "", profileImage = "", storyCount = 0),
        Shelter(id = "", name = "구교환 보호소", area = "서울", phone = "", profileImage = "", storyCount = 0),
        Shelter(id = "", name = "이옥섭 보호소", area = "경기도", phone = "", profileImage = "", storyCount = 0),
        Shelter(id = "", name = "사랑 보호소", area = "경기도", phone = "", profileImage = "", storyCount = 0),
        Shelter(id = "", name = "로미오 유기견 보호소", area = "경기도", phone = "", profileImage = "", storyCount = 0),
    )
}
