package teka.android.organiks_platform_android.presentation.feature_dashborad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.domain.repository.RemoteEggRecordsRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel  @Inject constructor(
    private val repository: DbRepository,
    private val eggRecordsRepository: RemoteEggRecordsRepository,
    ): ViewModel() {


    private val _eggCollections = MutableStateFlow<List<EggCollection>>(emptyList())
    val eggCollections: StateFlow<List<EggCollection>> = _eggCollections.asStateFlow()

    private val _milkCollections = MutableStateFlow<List<MilkCollection>>(emptyList())
    val milkCollections: StateFlow<List<MilkCollection>> = _milkCollections.asStateFlow()

    private val _fruitCollections = MutableStateFlow<List<FruitCollectionEntity>>(emptyList())
    val fruitCollections: StateFlow<List<FruitCollectionEntity>> = _fruitCollections.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing

    private val _totalNotBackedUpCount = MutableStateFlow(0)
    val totalNotBackedUpCount: StateFlow<Int> = _totalNotBackedUpCount

    private val _remoteEggCollections = MutableStateFlow<List<EggCollectionResult>>(emptyList())
    val remoteEggCollections: StateFlow<List<EggCollectionResult>> = _remoteEggCollections.asStateFlow()

    // State for loading indicator and error states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage


//    init {
//        viewModelInitialization()
//    }

    fun viewModelInitialization() {
        viewModelScope.launch {
            fetchAllRemoteEggRecords()
            fetchEggCollections()
            fetchMilkCollections()
            refreshNotBackedUpCollections()
        }

    }

    fun fetchAllRemoteEggRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                eggRecordsRepository.getAllEggCollections().collect { eggs ->
                    _remoteEggCollections.value = eggs
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

    // Fetch and update milk collections in your ViewModel
    private fun fetchEggCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEggCollections.collectLatest { eggCollections ->
                _eggCollections.value = eggCollections
            }
        }
    }

    private fun fetchMilkCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMilkCollection.collectLatest { milkCollections ->
                _milkCollections.value = milkCollections
            }
        }
    }

    private fun fetchFruitCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFruitCollections.collectLatest { fruitCollections ->
                _fruitCollections.value = fruitCollections
            }
        }
    }

    private suspend fun fetchNotBackedUpCollections() {
        withContext(Dispatchers.IO) {


            val notBackedUpEggCollections = repository.getEggCollections
                .map { eggCollections ->
                    eggCollections.filter { !it.isBackedUp }
                }

            val notBackedUpMilkCollections = repository.getMilkCollection
                .map { milkCollections ->
                    milkCollections.filter { !it.isBackedUp }
                }

            val totalNotBackedUp =
                combine(notBackedUpEggCollections, notBackedUpMilkCollections) { eggs, milk ->
                    eggs.size + milk.size
                }

            val total = totalNotBackedUp.first()
            _totalNotBackedUpCount.value = total
        }


    }

    fun refreshNotBackedUpCollections() {
        viewModelScope.launch {
            fetchNotBackedUpCollections()
        }
    }

}
