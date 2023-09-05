package teka.android.organiks_platform_android.presentation.records.production.productionHome


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.data.room_remote_sync.RemoteDataUpdater
import teka.android.organiks_platform_android.repository.DbRepository
import javax.inject.Inject

@HiltViewModel
class ProductionHomeViewModel @Inject constructor(
    private val repository: DbRepository,
    private val remoteDataUpdater: RemoteDataUpdater
): ViewModel() {

    private val _eggCollections = MutableStateFlow<List<EggCollection>>(emptyList())
    val eggCollections: StateFlow<List<EggCollection>> = _eggCollections.asStateFlow()

    private val _milkCollections = MutableStateFlow<List<MilkCollection>>(emptyList())
    val milkCollections: StateFlow<List<MilkCollection>> = _milkCollections.asStateFlow()


    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing

    init {
        viewModelInitialization()
    }

    fun viewModelInitialization(){
        fetchEggCollections()
        fetchMilkCollections()
    }

    // Fetch and update milk collections in your ViewModel
    private fun fetchEggCollections() {
        viewModelScope.launch {
            repository.getEggCollections.collectLatest { eggCollections ->
                _eggCollections.value = eggCollections
            }
        }
    }

    private fun fetchMilkCollections() {
        viewModelScope.launch {
            repository.getMilkCollection.collectLatest { milkCollections ->
                _milkCollections.value = milkCollections
            }
        }
    }


    fun syncRoomDbToRemote() {
        viewModelScope.launch {
            _isSyncing.value = true // Set isSyncing to true when synchronization starts
            try {
                // Filter and get eggCollections with status backedUp == false
                val notBackedUpEggCollections = eggCollections.value.filter { eggCollection ->
                    !eggCollection.isBackedUp
                }
                val notBackedUpMilkCollections = milkCollections.value.filter { milkCollection ->
                    !milkCollection.isBackedUp
                }
                remoteDataUpdater.updateRemoteEggCollectionData(notBackedUpEggCollections, repository)
                remoteDataUpdater.updateRemoteMilkCollectionData(notBackedUpMilkCollections, repository)
                // Synchronization completed successfully
            } catch (e: Exception) {
                // Handle synchronization failure
            } finally {
                _isSyncing.value = false // Set isSyncing back to false when synchronization is done
            }
        }
    }
}