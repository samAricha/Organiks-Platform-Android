package teka.android.organiks_platform_android.data.repositoryImpl

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseRemoteDatabase
import teka.android.organiks_platform_android.data.remote.models.toFruitCollectionEntity
import teka.android.organiks_platform_android.data.remote.models.toFruitCollectionEntityList
import teka.android.organiks_platform_android.data.room.RoomRepository
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.domain.repository.def.FruitCollectionRepository
import teka.android.organiks_platform_android.util.Resource


class FruitCollectionsRepositoryImpl @Inject constructor(
    private val roomRepository: RoomRepository,
    private val firebaseRemoteDatabase1: FirebaseRemoteDatabase,
) : FruitCollectionRepository {

    // Sync fruit collections from Room to Firebase
    override fun syncFruitCollections(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading()) // Emit loading state before syncing

        try {
            // Fetch all unsynced fruit collections from Room database
            val unsyncedFruitCollections = roomRepository.getUnsyncedFruitCollections()

            // Iterate over the unsynced fruit collections and sync them to Firebase
            for (collection in unsyncedFruitCollections) {
                // Add fruit collection to Firebase
                firebaseRemoteDatabase1.addFruitCollection(collection)

                // After successful sync, mark the collection as synced in Room
                roomRepository.markFruitCollectionAsSynced(collection.uuid)
            }

            emit(Resource.Success(Unit)) // Emit success after syncing all fruit collections
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error syncing fruit collections")) // Emit error if something goes wrong
        }
    }

    override fun getFruitCollections(): Flow<Resource<List<FruitCollectionEntity>>> =  flow{
        emit(Resource.Loading())
        try {
            val caseList = firebaseRemoteDatabase1.fetchFruitCollectionList()
            emit(Resource.Success(caseList.toFruitCollectionEntityList()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching churches"))
        }
    }

    override fun getFruitCollectionById(id: String): Flow<Resource<FruitCollectionEntity>> = flow{
        emit(Resource.Loading())
        try {
            val case = firebaseRemoteDatabase1.fetchFruitCollectionById(id)
            emit(Resource.Success(case.toFruitCollectionEntity()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching case with ID: $id"))
        }    }

    override fun createFruitCollection(case: FruitCollectionEntity): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        try {
            firebaseRemoteDatabase1.addFruitCollection(case)
            emit(Resource.Success(Unit)) // Indicate success with no specific data
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error creating case"))
        }
    }

}