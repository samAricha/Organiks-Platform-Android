package teka.android.organiks_platform_android.data.room_remote_sync

import teka.android.organiks_platform_android.data.room.models.EggCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.repository.Repository


class RemoteDataUpdater {

    suspend fun updateRemoteEggCollectionData(eggCollections: List<EggCollection>, repository: Repository) {
//        var updatedEggCollections: List<EggCollection>
        withContext(Dispatchers.IO) {
            try {
                eggCollections.forEach { eggCollection ->
                    val response = RetrofitProvider.createEggCollectionService().createRemoteEggCollection(eggCollection)
                    if (response != null) {
                        if (response.success){
                            eggCollection.isBackedUp = true
                            repository.updateEggCollection(eggCollection)
                        }
                    }

                }
                // Handle the response if needed
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }


}