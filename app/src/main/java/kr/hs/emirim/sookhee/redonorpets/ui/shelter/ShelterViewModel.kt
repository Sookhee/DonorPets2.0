package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Donation
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter
import kr.hs.emirim.sookhee.redonorpets.domain.entity.StoryFeed
import kr.hs.emirim.sookhee.redonorpets.domain.usecase.GetShelterListUseCase
import kr.hs.emirim.sookhee.redonorpets.ui.UiState

/**
 *  ShelterViewModel.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class ShelterViewModel @ViewModelInject constructor(
    private val getShelterListUseCase: GetShelterListUseCase
) : ViewModel() {
    private val _shelterState = MutableStateFlow<UiState>(UiState.Loading)
    val shelterState: StateFlow<UiState> = _shelterState

    private val _storyList = MutableStateFlow<List<StoryFeed>>(emptyList())
    val storyList: StateFlow<List<StoryFeed>> = _storyList

    private val _shelterData = MutableStateFlow(Shelter())
    val shelterData: StateFlow<Shelter> = _shelterData

    fun getShelterList() {
        viewModelScope.launch {
            val shelterList = getShelterListUseCase()
            _shelterState.value = UiState.Success(shelterList)
        }
    }

    fun getShelterData(id: String) {
        _shelterData.value = FAKE_SHELTER_DATA
        getShelterStory(id)
    }

    private fun getShelterStory(id: String) {
        _storyList.value = FAKE_STORY_LIST
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

    private val FAKE_SHELTER_DATA = Shelter(
        id = "",
        name = "도너츠 유기견 보호소",
        area = "서울",
        phone = "010-1234-1234",
        profileImage = "",
        storyCount = 10,
        donorCount = 3,
        likeCount = 291,
        objectList = listOf(
            Donation(id = "", name = "수건", point = 0, imageUri = "", quantity = 0),
            Donation(id = "", name = "사료", point = 0, imageUri = "", quantity = 0),
        )
    )

    private val FAKE_STORY_LIST = listOf(
        StoryFeed("story1", "title 1", "1", "First Shelter", "서울", "", "2022.01.02", 9, 0),
    )
}
