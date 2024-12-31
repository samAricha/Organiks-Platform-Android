package teka.android.organiks_platform_android.domain.repository.def

import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.util.Resource

interface FruitCollectionRepository {
    fun getFruitCollections(): Flow<Resource<List<FruitCollectionEntity>>>
    fun getFruitCollectionById(caseId: String): Flow<Resource<FruitCollectionEntity>>
    fun createFruitCollection(case: FruitCollectionEntity): Flow<Resource<Unit>>
    fun syncFruitCollections(): Flow<Resource<Unit>>
}