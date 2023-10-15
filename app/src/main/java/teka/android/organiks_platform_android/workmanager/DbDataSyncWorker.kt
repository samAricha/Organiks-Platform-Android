package teka.android.organiks_platform_android.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.networking.ConnectivityObserver
import teka.android.organiks_platform_android.networking.NetworkConnectivityObserver
import teka.android.organiks_platform_android.presentation.records.production.productionHome.ProductionHomeViewModel
import javax.inject.Inject


@HiltWorker
class DbDataSyncWorker @Inject constructor(
    appContext: Context,
    params: WorkerParameters,
    private val productionHomeViewModel: ProductionHomeViewModel // Inject your ViewModel

) : CoroutineWorker(appContext, params) {
    private lateinit var connectivityObserver: ConnectivityObserver


    override suspend fun doWork(): Result {
        val connectivityStatus = withContext(Dispatchers.IO) {
            // Perform network operations, such as checking connectivity
            connectivityObserver = NetworkConnectivityObserver(applicationContext)
            connectivityObserver.observe().first()
        }

        if (connectivityStatus == ConnectivityObserver.Status.Available) {
            // Check if not already syncing
            if (!productionHomeViewModel.isSyncing.value) {
                productionHomeViewModel.syncRoomDbToRemote()
            }
        }

        return Result.success()
    }
}