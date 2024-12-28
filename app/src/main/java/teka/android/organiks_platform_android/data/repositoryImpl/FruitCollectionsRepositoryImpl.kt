package teka.android.organiks_platform_android.data.repositoryImpl

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseRemoteDatabase
import teka.android.organiks_platform_android.data.remote.models.toFruitCollectionEntity
import teka.android.organiks_platform_android.data.remote.models.toFruitCollectionEntityList
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.domain.repository.FruitCollectionRepository
import teka.android.organiks_platform_android.util.Resource


class FruitCollectionsRepositoryImpl @Inject constructor(
    private val firebaseRemoteDatabase: FirebaseRemoteDatabase,
) : FruitCollectionRepository {

    override fun getFruitCollections(): Flow<Resource<List<FruitCollectionEntity>>> =  flow{
        emit(Resource.Loading())
        try {
            val caseList = firebaseRemoteDatabase.fetchFruitCollectionList()
            emit(Resource.Success(caseList.toFruitCollectionEntityList()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching churches"))
        }
    }

    override fun getFruitCollectionById(id: String): Flow<Resource<FruitCollectionEntity>> = flow{
        emit(Resource.Loading())
        try {
            val case = firebaseRemoteDatabase.fetchFruitCollectionById(id)
            emit(Resource.Success(case.toFruitCollectionEntity()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching case with ID: $id"))
        }    }

    override fun createFruitCollection(case: FruitCollectionEntity): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        try {
            firebaseRemoteDatabase.addCase(case)
            emit(Resource.Success(Unit)) // Indicate success with no specific data
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error creating case"))
        }
    }

}