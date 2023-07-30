package teka.android.organiks_platform_android.data.room_remote_sync

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.data.remote.retrofit.toEggCollection
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.repository.Repository


class FetchRemoteData {
    suspend fun fetchRemoteDataAndSaveLocally(repository: Repository){
        withContext(Dispatchers.IO) {
            try {
                Log.d("INSIDE TRY", "FIRST LINE")
                val response = RetrofitProvider.createVisitorListService().getVisitorList()
                val eggCollections: List<EggCollection> = response.results.map { it.toEggCollection() }
                val repositoryResponse = repository.saveRemoteEggCollections(eggCollections)
                Log.d("REPOSITORY RESPONSE", repositoryResponse.toString())

            } catch (e: Exception) {
                Log.d("FETCH_ERROR", e.message.toString())

            }
        }
    }
}