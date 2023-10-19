package teka.android.organiks_platform_android.data.room_remote_sync

import android.content.Context
import android.util.Log
import android.widget.Toast
import teka.android.organiks_platform_android.data.room.models.EggCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.data.remote.retrofit.models.toEggCollectionRequest
import teka.android.organiks_platform_android.data.remote.retrofit.models.toFruitCollectionRequest
import teka.android.organiks_platform_android.data.remote.retrofit.models.toMilkCollectionRequest
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.repository.DbRepository
import javax.inject.Inject


sealed class UpdateResult {
    data class Success(val message: String) : UpdateResult()
    data class Failure(val errorMessage: String) : UpdateResult()
}

class RemoteDataUpdater @Inject constructor(private val appContext: Context) {

    suspend fun updateRemoteEggCollectionData(eggCollections: List<EggCollection>, repository: DbRepository): UpdateResult {

        return try{
            withContext(Dispatchers.IO) {
                eggCollections.forEach { eggCollection ->
                    val eggCollectionRequest = eggCollection.toEggCollectionRequest()

                        val response = RetrofitProvider.createEggCollectionService().createRemoteEggCollection(eggCollectionRequest)
                    if (response.success){
                        eggCollection.isBackedUp = true
                        repository.updateEggCollection(eggCollection)
//                        Toast.makeText(appContext, "Sync successful.", Toast.LENGTH_SHORT).show()
                         UpdateResult.Success("Data updated successfully.")
                    }else{
                        Toast.makeText(appContext, "Sync failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
                UpdateResult.Success("Data updated successfully.")
            }
        } catch (e: Exception) {
                e.printStackTrace()
                Log.d(">>>>>>>>REMOTEEGG", e.message.toString())
                UpdateResult.Failure("Error updating data: ${e.message}")
        }
    }


    suspend fun updateRemoteMilkCollectionData(milkCollections: List<MilkCollection>, repository: DbRepository):UpdateResult {
        return try {
            withContext(Dispatchers.IO) {
                milkCollections.forEach { milkCollection ->
                    val milkCollectionRequest = milkCollection.toMilkCollectionRequest()

                    val response = RetrofitProvider.createMilkCollectionService().createRemoteMilkCollection(milkCollectionRequest)

                    if (response.success){
                        milkCollection.isBackedUp = true
                        repository.updateMilkCollection(milkCollection)
                    }
                }
                UpdateResult.Success("Data updated successfully.")
            }
        }catch (e: Exception) {
            UpdateResult.Failure("Error updating data: ${e.message}")
        }
    }


    suspend fun updateRemoteFruitCollectionData(fruitCollections: List<FruitCollectionEntity>, repository: DbRepository):UpdateResult {
        return try {
            withContext(Dispatchers.IO) {
                fruitCollections.forEach { fruitCollection ->
                    val fruitCollectionRequest = fruitCollection.toFruitCollectionRequest()

                    val response = RetrofitProvider
                        .createFruitCollectionService()
                        .createRemoteFruitCollection(fruitCollectionRequest)

                    if (response.success){
                        fruitCollection.isBackedUp = true
                        repository.updateFruitCollection(fruitCollection)
                    }
                }
                UpdateResult.Success("Data updated successfully.")
            }
        }catch (e: Exception) {
            e.let { Log.d("Fruit sync error", it.toString()) }
            UpdateResult.Failure("Error updating Fruit data: ${e.message}")
        }
    }
}