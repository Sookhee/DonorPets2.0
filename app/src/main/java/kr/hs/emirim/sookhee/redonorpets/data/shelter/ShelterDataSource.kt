package kr.hs.emirim.sookhee.redonorpets.data.shelter

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 *  ShelterDataStore.kt
 *
 *  Created by Minji Jeong on 2022/01/26
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class ShelterDataSource @Inject constructor() {
    val db = Firebase.firestore

    suspend fun getShelterList(): List<DocumentSnapshot> {
        val result = db.collection(COLLECTION_SHELTER)
            .get()
            .await()

        return result.documents
    }

    suspend fun getShelterStoryCount(shelterId: String): Int {
        val result = db.collection(COLLECTION_STORY)
            .whereEqualTo("shelterId", shelterId)
            .get()
            .await()

        return result.documents.size
    }

    companion object {
        private const val COLLECTION_SHELTER = "shelter"
        private const val COLLECTION_STORY = "story"
    }
}
