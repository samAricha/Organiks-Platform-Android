package teka.android.organiks_platform_android.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.dtos.ApiResponseHandler
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionResult
import teka.android.organiks_platform_android.data.remote.services.MilkCollectionService
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteMilkRecordsRepository(
    private val milkCollectionService: MilkCollectionService,
) {

    suspend fun getAllMilkCollections(): Flow<List<MilkCollectionResult>> {
        return flow {
            try {
//                val response: ApiResponseHandler<List<MilkCollectionResult>> = milkCollectionService.getAllMilkCollection()
//                Timber.tag(">>>milk LIST").d(response.toString())
//                response.data.let {
//                    if (it != null) {
//                        emit(it)
//                    }
//                }

                val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                val result = withContext(Dispatchers.IO) {
                    suspendCoroutine<List<MilkCollectionResult>> { continuation ->
                        db.collection("MilkCollections").get()
                            .addOnSuccessListener { queryDocumentSnapshots ->
                                if (!queryDocumentSnapshots.isEmpty) {
                                    val list = queryDocumentSnapshots.documents.mapNotNull { document ->
                                        document.toObject(MilkCollectionResult::class.java)
                                    }
                                    continuation.resume(list)
                                } else {
                                    continuation.resume(emptyList())
                                }
                            }
                            .addOnFailureListener { exception ->
                                continuation.resume(emptyList())
                            }
                    }
                }
                emit(result)


            } catch (e: Exception) {
                Timber.tag(">>>MILK LIST ERROR").e(e)
                // Handle exceptions here (e.g., network issues)
                emit(emptyList())
            }
        }
    }


}
