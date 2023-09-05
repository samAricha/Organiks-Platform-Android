package teka.android.organiks_platform_android.presentation.dashborad

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
class DashboardViewModel  @Inject constructor(
    private val repository: DbRepository,
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

    fun viewModelInitialization() {
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

    // Computed property to get the total number of eggs collected
    val totalEggsCollected: Int
        get() = _eggCollections.value.sumOf { it.qty.toInt() }

    // Computed property to get the total amount of milk collected
    val totalMilkCollected: Double
        get() = _milkCollections.value.sumOf { it.qty.toDouble() }
}