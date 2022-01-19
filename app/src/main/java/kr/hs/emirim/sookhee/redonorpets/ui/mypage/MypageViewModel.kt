package kr.hs.emirim.sookhee.redonorpets.ui.mypage

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Badge
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter

/**
 *  MypageViewModel.kt
 *
 *  Created by Minji Jeong on 2022/01/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class MypageViewModel : ViewModel() {
    private val _badgeList = MutableStateFlow<List<Badge>>(emptyList())
    val badgeList: StateFlow<List<Badge>> = _badgeList

    private val _myShelterList = MutableStateFlow<List<Shelter>>(emptyList())
    val myShelterList: StateFlow<List<Shelter>> = _myShelterList

    fun getMyBadgeList() {
        _badgeList.value = FAKE_BADGE_LIST
    }

    fun getMyShelterList() {
        _myShelterList.value = FAKE_SHELTER_LIST
    }

    companion object {
        val FAKE_SHELTER_LIST = listOf(
            Shelter(id = "", name = "호호 유기견 보호소", area = "서울", phone = "", profileImage = "", storyCount = 0),
            Shelter(id = "", name = "이옥섭 보호소", area = "경기도", phone = "", profileImage = "", storyCount = 0),
            Shelter(id = "", name = "로미오 유기견 보호소", area = "경기도", phone = "", profileImage = "", storyCount = 0),
        )

        val FAKE_BADGE_LIST = listOf(
            Badge("", isClear = false, title = "TITLE1", imageUrl = ""),
            Badge("", isClear = false, title = "TITLE2", imageUrl = ""),
        )
    }
}
