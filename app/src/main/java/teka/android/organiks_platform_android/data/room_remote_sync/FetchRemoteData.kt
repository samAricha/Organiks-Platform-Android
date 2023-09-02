package teka.android.organiks_platform_android.data.room_remote_sync

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.data.remote.retrofit.toEggCollection
import teka.android.organiks_platform_android.data.remote.retrofit.toMilkCollection
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.repository.DbRepository


class FetchRemoteData {
    suspend fun fetchRemoteEggCollectionDataAndSaveLocally(repository: DbRepository){
        withContext(Dispatchers.IO) {
            try {
                Log.d("INSIDE TRY", "FIRST LINE")
                val response = RetrofitProvider.createEggCollectionService().getEggCollections()
                val eggCollections: List<EggCollection> = response.results.map { it.toEggCollection() }
                val repositoryResponse = repository.saveRemoteEggCollections(eggCollections)
                Log.d("REPOSITORY RESPONSE", repositoryResponse.toString())

            } catch (e: Exception) {
                Log.d("FETCH_ERROR", e.message.toString())

            }
        }
    }


    suspend fun fetchRemoteMilkCollectionDataAndSaveLocally(repository: DbRepository){
        withContext(Dispatchers.IO) {
            try {
                Log.d("INSIDE TRY", "FIRST LINE")
                val response = RetrofitProvider.createMilkCollectionService().getMilkCollection()
                val milkCollections: List<MilkCollection> = response.results.map { it.toMilkCollection() }
                val repositoryResponse = repository.saveRemoteMilkCollections(milkCollections)
                Log.d("REPOSITORY RESPONSE", repositoryResponse.toString())

            } catch (e: Exception) {
                Log.d("FETCH_ERROR", e.message.toString())

            }
        }
    }
}