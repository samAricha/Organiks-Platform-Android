package teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionResult
import teka.android.organiks_platform_android.domain.repository.RemoteEggRecordsRepository
import teka.android.organiks_platform_android.domain.repository.RemoteFruitRecordsRepository
import teka.android.organiks_platform_android.domain.repository.RemoteMilkRecordsRepository
import timber.log.Timber
import javax.inject.Inject

data class SnackbarData(val message: String)


@HiltViewModel
class RemoteRecordsViewModel @Inject constructor(
    private val eggRecordsRepository: RemoteEggRecordsRepository,
    private val milkRecordsRepository: RemoteMilkRecordsRepository,
    private val fruitsRecordsRepository: RemoteFruitRecordsRepository,
): ViewModel() {

    private val _eggCollections = MutableStateFlow<List<EggCollectionResult>>(emptyList())
    val eggCollections: StateFlow<List<EggCollectionResult>> = _eggCollections.asStateFlow()

    private val _milkCollections = MutableStateFlow<List<MilkCollectionResult>>(emptyList())
    val milkCollections: StateFlow<List<MilkCollectionResult>> = _milkCollections.asStateFlow()

    private val _fruitCollections = MutableStateFlow<List<FruitCollectionDto>>(emptyList())
    val fruitCollections: StateFlow<List<FruitCollectionDto>> = _fruitCollections.asStateFlow()


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

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage



    init {
        viewModelInitialization()
    }

    private fun viewModelInitialization(){
        viewModelScope.launch {
            fetchAllEggRecords()
            fetchAllMilkCollections()
            fetchAllFruitCollections()
        }
    }




    private fun fetchAllEggRecords() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                eggRecordsRepository.getAllEggCollections().collect { eggs ->
                    _eggCollections.value = eggs
                    Timber.tag(">>>EGGS LIST").d(eggs.toString())

                    // Serialize to JSON
//                    val jsonString = Json.encodeToString(eggs)
//                    Timber.tag(">>>serialized eggs").d(jsonString)
                }

                _successMessage.value = "Data Fetched successfully"

            } catch (e: Exception) {
                Timber.tag("Error fetching egg records: ${e.localizedMessage}")

                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun fetchAllFruitCollections() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                fruitsRecordsRepository.getAllFruitCollections().collect { fruits ->
                    _fruitCollections.value = fruits
                    Timber.tag(">>>FRUIT LIST").d(fruits.toString())
                }
                _successMessage.value = "Data Fetched successfully"

            } catch (e: Exception) {
                _errorMessage.value = e.message

            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchAllMilkCollections() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                milkRecordsRepository.getAllMilkCollections().collect { milk ->
                    _milkCollections.value = milk
                    Timber.tag(">>>MILK LIST").d(milk.toString())
                }
                _successMessage.value = "Data Fetched successfully"

            } catch (e: Exception) {
                _errorMessage.value = e.message

            } finally {
                _isLoading.value = false
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

}