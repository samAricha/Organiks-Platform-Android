package teka.android.organiks_platform_android.data.room_remote_sync

import teka.android.organiks_platform_android.data.network.ApiService.Companion.apiService
import teka.android.organiks_platform_android.data.room.models.EggCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RemoteDataUpdater {

    suspend fun updateRemoteEggCollectionData(eggCollections: List<EggCollection>) {
        withContext(Dispatchers.IO) {
            try {
                eggCollections.forEach { eggCollection ->
                    val response = apiService?.createEggCollection(eggCollection)

                }
                // Handle the response if needed
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }


}