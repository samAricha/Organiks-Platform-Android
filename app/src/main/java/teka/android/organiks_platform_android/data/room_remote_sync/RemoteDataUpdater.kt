package teka.android.organiks_platform_android.data.room_remote_sync

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import teka.android.organiks_platform_android.data.room.models.EggCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.data.remote.retrofit.models.toEggCollectionRequest
import teka.android.organiks_platform_android.data.remote.retrofit.models.toMilkCollectionRequest
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.repository.DataStoreRepository
import teka.android.organiks_platform_android.repository.DbRepository
import javax.inject.Inject


class RemoteDataUpdater @Inject constructor(private val appContext: Context) {

    suspend fun updateRemoteEggCollectionData(eggCollections: List<EggCollection>, repository: DbRepository) {
        withContext(Dispatchers.IO) {
            try {
                eggCollections.forEach { eggCollection ->
                    val eggCollectionRequest = eggCollection.toEggCollectionRequest()

                        val response = RetrofitProvider.createEggCollectionService().createRemoteEggCollection(eggCollectionRequest)
                    if (response.success){
                        eggCollection.isBackedUp = true
                        repository.updateEggCollection(eggCollection)
                        Toast.makeText(appContext, "Sync successful.", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(appContext, "Sync failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
                // Handle the response if needed
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(">>>>>>>>REMOTEEGG", e.message.toString())
            }
        }
    }


    suspend fun updateRemoteMilkCollectionData(milkCollections: List<MilkCollection>, repository: DbRepository) {
        withContext(Dispatchers.IO) {
            try {
                milkCollections.forEach { milkCollection ->
                    val milkCollectionRequest = milkCollection.toMilkCollectionRequest()

                    val response = RetrofitProvider.createMilkCollectionService().createRemoteMilkCollection(milkCollectionRequest)

                    if (response.success){
                        milkCollection.isBackedUp = true
                        repository.updateMilkCollection(milkCollection)
                    }
                }
                // Handle the response if needed
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }


}