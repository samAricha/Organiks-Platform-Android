package teka.android.organiks_platform_android.presentation.feature_records.production.remoteRecords


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.domain.repository.RemoteEggRecordsRepository
import timber.log.Timber
import javax.inject.Inject

data class SnackbarData(val message: String)


@HiltViewModel
class RemoteRecordsViewModel @Inject constructor(
    private val eggRecordsRepository: RemoteEggRecordsRepository,
): ViewModel() {

    private val _eggCollections = MutableStateFlow<List<EggCollectionResult>>(emptyList())
    val eggCollections: StateFlow<List<EggCollectionResult>> = _eggCollections.asStateFlow()

    private val _milkCollections = MutableStateFlow<List<MilkCollection>>(emptyList())
    val milkCollections: StateFlow<List<MilkCollection>> = _milkCollections.asStateFlow()

    private val _fruitCollections = MutableStateFlow<List<FruitCollectionEntity>>(emptyList())
    val fruitCollections: StateFlow<List<FruitCollectionEntity>> = _fruitCollections.asStateFlow()


    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing

    val eggSnackbarMessage = mutableStateOf<String?>(null)
    val milkSnackbarMessage = mutableStateOf<String?>(null)
    val fruitSnackbarMessage = mutableStateOf<String?>(null)

    private val _snackbarData = MutableStateFlow<SnackbarData?>(null)
    val snackbarData: StateFlow<SnackbarData?> = _snackbarData


    // State for loading indicator and error states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isCreatingProperty = MutableStateFlow(false)
    val isCreatingProperty: StateFlow<Boolean> = _isCreatingProperty

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage



    init {
        viewModelInitialization()
    }

    private fun viewModelInitialization(){
        fetchAllEggRecords()
        fetchMilkCollections()
        fetchFruitCollections()
    }




    fun fetchAllEggRecords() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                eggRecordsRepository.getAllEggCollections().collect { eggs ->
                    _eggCollections.value = eggs
                    Timber.tag(">>>EGGS LIST").d(eggs.toString())
                }
                _successMessage.value = "Data Fetched successfully"

            } catch (e: Exception) {
                _errorMessage.value = e.message

            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun fetchFruitCollections() {
        viewModelScope.launch {
//            eggRecordsRepository.getFruitCollections.collectLatest { fruitCollections ->
//                _fruitCollections.value = fruitCollections
//            }
        }
    }

    private fun fetchMilkCollections() {
        viewModelScope.launch {
//            eggRecordsRepository.getMilkCollection.collectLatest { milkCollections ->
//                _milkCollections.value = milkCollections
//            }
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

}