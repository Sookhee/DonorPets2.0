package kr.hs.emirim.sookhee.redonorpets.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kr.hs.emirim.sookhee.redonorpets.domain.entity.StoryFeed
import kr.hs.emirim.sookhee.redonorpets.ui.UiState

/**
 *  HomeViewModel.kt
 *
 *  Created by Minji Jeong on 2022/01/06
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class HomeViewModel : ViewModel() {
    private val _storyState = MutableStateFlow<UiState>(UiState.Loading)
    val storyState: StateFlow<UiState> = _storyState

    fun getStoryList() {
        _storyState.value = UiState.Success(FAKE_STORY_LIST)
    }

    private val FAKE_STORY_LIST = listOf(
        StoryFeed("story1", "title 1", "1", "First Shelter", "서울", "", "2022.01.02", 9, 0),
        StoryFeed("story2", "title 2", "2", "Second Shelter", "경기도", "", "2022.01.06", 10, 2),
        StoryFeed("story3", "title 3", "2", "Second Shelter", "경기도", "", "2022.01.06", 30, 4),
    )
}
