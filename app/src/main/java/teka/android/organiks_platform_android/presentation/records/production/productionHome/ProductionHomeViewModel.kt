package teka.android.organiks_platform_android.presentation.records.production.productionHome


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.FruitCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.data.room_remote_sync.RemoteDataUpdater
import teka.android.organiks_platform_android.data.room_remote_sync.UpdateResult
import teka.android.organiks_platform_android.repository.DbRepository
import javax.inject.Inject

data class SnackbarData(val message: String)


@HiltViewModel
class ProductionHomeViewModel @Inject constructor(
    private val repository: DbRepository,
    private val remoteDataUpdater: RemoteDataUpdater
): ViewModel() {

    private val _eggCollections = MutableStateFlow<List<EggCollection>>(emptyList())
    val eggCollections: StateFlow<List<EggCollection>> = _eggCollections.asStateFlow()

    private val _milkCollections = MutableStateFlow<List<MilkCollection>>(emptyList())
    val milkCollections: StateFlow<List<MilkCollection>> = _milkCollections.asStateFlow()

    private val _fruitCollections = MutableStateFlow<List<FruitCollection>>(emptyList())
    val fruitCollections: StateFlow<List<FruitCollection>> = _fruitCollections.asStateFlow()


    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing

    val eggSnackbarMessage = mutableStateOf<String?>(null)
    val milkSnackbarMessage = mutableStateOf<String?>(null)

    private val _snackbarData = MutableStateFlow<SnackbarData?>(null)
    val snackbarData: StateFlow<SnackbarData?> = _snackbarData



    init {
        viewModelInitialization()
    }

    fun viewModelInitialization(){
        fetchEggCollections()
        fetchMilkCollections()
        fetchFruitCollections()
    }

    // Fetch and update milk collections in your ViewModel
    private fun fetchEggCollections() {
        viewModelScope.launch {
            repository.getEggCollections.collectLatest { eggCollections ->
                _eggCollections.value = eggCollections
            }
        }
    }

    private fun fetchFruitCollections() {
        viewModelScope.launch {
            repository.getFruitCollections.collectLatest { fruitCollections ->
                _fruitCollections.value = fruitCollections
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

    // Function to trigger a Snackbar
    fun showSnackbar(message: String) {
        _snackbarData.value = SnackbarData(message)
    }

    // Function to clear the Snackbar
    fun clearSnackbar() {
        _snackbarData.value = null
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
                val notBackedUpFruitCollections = fruitCollections.value.filter { fruitCollection ->
                    !fruitCollection.isBackedUp
                }
                val result =remoteDataUpdater.updateRemoteEggCollectionData(notBackedUpEggCollections, repository)
                val result2 =remoteDataUpdater.updateRemoteMilkCollectionData(notBackedUpMilkCollections, repository)
                // Synchronization completed successfully
                when (result) {
                    is UpdateResult.Success -> {
                        eggSnackbarMessage.value = result.message
                        showSnackbar(eggSnackbarMessage.value!!)
                    }
                    is UpdateResult.Failure -> {
                        eggSnackbarMessage.value = result.errorMessage
                        showSnackbar(eggSnackbarMessage.value!!)
                    }
                }
                when (result2) {
                    is UpdateResult.Success -> {
                        milkSnackbarMessage.value = result2.message
                        showSnackbar(milkSnackbarMessage.value!!)
                    }
                    is UpdateResult.Failure -> {
                        milkSnackbarMessage.value = result2.errorMessage
                        showSnackbar(milkSnackbarMessage.value!!)
                    }
                }
            } catch (e: Exception) {
                // Handle synchronization failure
            } finally {
                _isSyncing.value = false // Set isSyncing back to false when synchronization is done
            }
        }
    }
}