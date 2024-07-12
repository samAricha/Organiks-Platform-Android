package teka.android.organiks_platform_android.data.room_remote_sync

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.data.remote.retrofit.models.toMilkCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.domain.repository.DbRepository


class FetchRemoteData {

    suspend fun fetchAndSaveAccountTypes(repository: DbRepository){
        withContext(Dispatchers.IO) {
            try {
                Log.d("INSIDE TRY", "FIRST LINE")
//                val response = RetrofitProvider.createEggCollectionService().getAllEggCollections()
//                val eggCollections: List<EggCollection> = response.results.map { it.toEggCollection() }
//                val repositoryResponse = repository.saveRemoteEggCollections(eggCollections)
//                Log.d("REPOSITORY RESPONSE", repositoryResponse.toString())

            } catch (e: Exception) {
                Log.d("FETCH_ERROR", e.message.toString())

            }
        }
    }
    suspend fun fetchRemoteEggCollectionDataAndSaveLocally(repository: DbRepository){
        withContext(Dispatchers.IO) {
            try {
//                Log.d("INSIDE TRY", "FIRST LINE")
//                val response = RetrofitProvider.createEggCollectionService().getAllEggCollections()
//                val eggCollections: List<EggCollection> = response.results.map { it.toEggCollection() }
//                val repositoryResponse = repository.saveRemoteEggCollections(eggCollections)
//                Log.d("REPOSITORY RESPONSE", repositoryResponse.toString())

            } catch (e: Exception) {
                Log.d("FETCH_ERROR", e.message.toString())

            }
        }
    }


    suspend fun fetchRemoteMilkCollectionDataAndSaveLocally(repository: DbRepository){
        withContext(Dispatchers.IO) {
            try {
                Log.d("INSIDE TRY", "FIRST LINE")
//                val response = RetrofitProvider.createMilkCollectionService().getAllMilkCollection()
//                val milkCollections: List<MilkCollection> = response.results.map { it.toMilkCollection() }
//                val repositoryResponse = repository.saveRemoteMilkCollections(milkCollections)
//                Log.d("REPOSITORY RESPONSE", repositoryResponse.toString())

            } catch (e: Exception) {
                Log.d("FETCH_ERROR", e.message.toString())

            }
        }
    }
}