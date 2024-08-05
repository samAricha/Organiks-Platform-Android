package teka.android.organiks_platform_android.presentation.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room_remote_sync.RemoteDataUpdater
import teka.android.organiks_platform_android.domain.repository.DbRepository
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: DbRepository,
    private val remoteDataUpdater: RemoteDataUpdater
): ViewModel() {

//    private val _memberCount = MutableStateFlow<EntityCountResult<Int>>(EntityCountResult.Success(0))
//    val memberCount: StateFlow<EntityCountResult<Int>> get() = _memberCount
//
//    private val _chamaaAccountsCount = MutableStateFlow<EntityCountResult<Int>>(EntityCountResult.Success(0))
//    val chamaaAccountsCount: StateFlow<EntityCountResult<Int>> get() = _chamaaAccountsCount

    fun getMemberCount() {
        viewModelScope.launch {
//            _memberCount.value = repository.getTotalMembers()
//            _chamaaAccountsCount.value = repository.getTotalChamaaAccounts()
        }
    }

}