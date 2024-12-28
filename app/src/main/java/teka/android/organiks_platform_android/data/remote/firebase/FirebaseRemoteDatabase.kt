package teka.android.organiks_platform_android.data.remote.firebase

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import teka.android.organiks_platform_android.data.remote.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity


class FirebaseRemoteDatabase(
    private val caseCollection: CollectionReference
) {
    suspend fun fetchFruitCollectionList(): List<FruitCollectionDto> {
        val snapshot = caseCollection.get().await()
        return snapshot.toObjects(FruitCollectionDto::class.java)
    }


    // Example method to fetch a case by ID from Firebase
    suspend fun fetchFruitCollectionById(fruitCollectionId: String): FruitCollectionDto {
        // Replace with the actual Firebase query logic
        val caseSnapshot = caseCollection.document(fruitCollectionId).get().await()
        return caseSnapshot.toObject(FruitCollectionDto::class.java)
            ?: throw Exception("Fruit Colleection with ID $fruitCollectionId not found")
    }


    suspend fun addCase(fruitCollection: FruitCollectionEntity) {
        caseCollection.document(fruitCollection.uuid).set(fruitCollection).await()
    }


    suspend fun updateCase(updatedFruitCollection: FruitCollectionDto) {
        caseCollection.document(updatedFruitCollection.uuid).set(updatedFruitCollection).await()
    }

}