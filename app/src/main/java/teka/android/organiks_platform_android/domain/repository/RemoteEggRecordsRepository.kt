package teka.android.organiks_platform_android.domain.repository

import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.dtos.ApiResponseHandler
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.services.EggCollectionService
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.domain.repository.utils.retryApiRequestWithDelay
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteEggRecordsRepository(
    private val eggCollectionService: EggCollectionService,
) {

    suspend fun getAllEggCollections(): Flow<List<EggCollectionResult>> {
        return flow {
            try {
//                val response: ApiResponseHandler<List<EggCollectionResult>> = retryApiRequestWithDelay(
//                    retries = 3,
//                    delayMillis = 2000
//                ) {
//                    eggCollectionService.getAllEggCollections()
//                }
//                Timber.tag(">>>EGG LIST").d(response.toString())
//                response.data?.let {
//                    emit(it)
//                } ?: emit(emptyList())




                val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                val result = withContext(Dispatchers.IO) {
                    suspendCoroutine<List<EggCollectionResult>> { continuation ->
                        db.collection("EggCollections").get()
                            .addOnSuccessListener { queryDocumentSnapshots ->
                                if (!queryDocumentSnapshots.isEmpty) {
                                    val list = queryDocumentSnapshots.documents.mapNotNull { document ->
                                        document.toObject(EggCollectionResult::class.java)
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
                Timber.tag(">>>EGG LIST ERROR").e(e)
                emit(emptyList())
            }
        }
    }






}
