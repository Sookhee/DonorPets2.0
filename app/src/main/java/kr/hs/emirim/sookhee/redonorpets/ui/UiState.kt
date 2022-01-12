package kr.hs.emirim.sookhee.redonorpets.ui

/**
 *  UiState.kt
 *
 *  Created by Minji Jeong on 2022/01/06
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

sealed class UiState {
    object Loading: UiState()
    data class Success<T>(val data: T): UiState()
    data class Error(val error: Throwable?): UiState()
}