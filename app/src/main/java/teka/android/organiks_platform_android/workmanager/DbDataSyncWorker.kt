package teka.android.organiks_platform_android.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.networking.ConnectivityObserver
import teka.android.organiks_platform_android.networking.NetworkConnectivityObserver
import teka.android.organiks_platform_android.presentation.feature_records.production.productionHome.ProductionHomeViewModel


@HiltWorker
class DbDataSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val productionHomeViewModel: ProductionHomeViewModel // Inject your ViewModel
) : CoroutineWorker(appContext, workerParams) {
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