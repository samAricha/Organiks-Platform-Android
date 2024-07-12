package teka.android.organiks_platform_android.presentation.feature_dashborad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.domain.repository.DbRepository
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

    private val _totalNotBackedUpCount = MutableStateFlow(0)
    val totalNotBackedUpCount: StateFlow<Int> = _totalNotBackedUpCount

    init {
        viewModelInitialization()
    }

    fun viewModelInitialization() {
        fetchEggCollections()
        fetchMilkCollections()
        refreshNotBackedUpCollections()
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

    private suspend fun fetchNotBackedUpCollections() {
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

    fun refreshNotBackedUpCollections() {
        viewModelScope.launch {
            fetchNotBackedUpCollections()
        }
    }

}
