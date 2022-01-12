package kr.hs.emirim.sookhee.redonorpets.ui.story

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Comment
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Story

/**
 *  StoryViewModel.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class StoryViewModel : ViewModel() {
    private val _feed = MutableStateFlow("")
    private val _shelter = MutableStateFlow("")

    private val _commentList = MutableStateFlow<List<Comment>>(emptyList())
    val commentList: StateFlow<List<Comment>> = _commentList

    val storyDetail = _feed.combine(_shelter) { feed, shelter ->
        Story(
            id = "",
            title = "TITLE",
            createDate = "2022.01.12",
            updateDate = "2022.01.12",
            content = listOf(),
            shelterId = "",
            shelterName = "도너츠 보호소",
            shelterProfile = "",
            likeCount = 10
        )
    }

    fun getStoryDetail(feedId: String, shelterId: String) {
        getFeedData(feedId)
        getShelterData(shelterId)
        getCommendData(feedId)
    }

    private fun getFeedData(feedId: String) {
        _feed.value = feedId
    }

    private fun getShelterData(shelterId: String) {
        _shelter.value = shelterId
    }

    private fun getCommendData(feedId: String) {
        _commentList.value = listOf(
            Comment(id = "", name = "닉네임1", profileImage = "", comment = "댓글 111"),
            Comment(id = "", name = "닉네임2", profileImage = "", comment = "댓글 222\n줄바꿈 테스트\n한 줄 더!"),
            Comment(id = "", name = "닉네임3", profileImage = "", comment = "응원해요!"),
            Comment(id = "", name = "닉네임4", profileImage = "", comment = "댓글 4 ><\n안녕하세요"),
            Comment(id = "", name = "닉네임5", profileImage = "", comment = "댓글 555")
        )
    }
}
