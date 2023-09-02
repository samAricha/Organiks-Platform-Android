package teka.android.organiks_platform_android.data.room_remote_sync

import teka.android.organiks_platform_android.data.room.models.EggCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.data.remote.retrofit.models.toEggCollectionRequest
import teka.android.organiks_platform_android.data.remote.retrofit.models.toMilkCollectionRequest
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.repository.Repository


class RemoteDataUpdater {

    suspend fun updateRemoteEggCollectionData(eggCollections: List<EggCollection>, repository: Repository) {
        withContext(Dispatchers.IO) {
            try {
                eggCollections.forEach { eggCollection ->
                    val eggCollectionRequest = eggCollection.toEggCollectionRequest()

                    val response = RetrofitProvider.createEggCollectionService().createRemoteEggCollection(eggCollectionRequest)
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


    suspend fun updateRemoteMilkCollectionData(milkcollections: List<MilkCollection>, repository: Repository) {
        withContext(Dispatchers.IO) {
            try {
                milkcollections.forEach { milkCollection ->
                    val milkCollectionRequest = milkCollection.toMilkCollectionRequest()

                    val response = RetrofitProvider.createMilkCollectionService().createRemoteMilkCollection(milkCollectionRequest)
                    if (response != null) {
                        if (response.success){
                            milkCollection.isBackedUp = true
                            repository.updateMilkCollection(milkCollection)
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