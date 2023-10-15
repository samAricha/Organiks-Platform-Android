package teka.android.organiks_platform_android

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.networking.ConnectivityObserver
import teka.android.organiks_platform_android.networking.NetworkConnectivityObserver
import teka.android.organiks_platform_android.workmanager.DbDataSyncWorker
import javax.inject.Inject

@HiltAndroidApp
class OrganiksApplication: Application() {

    @Inject
    lateinit var workManager: WorkManager // Inject WorkManager

    @Inject
    lateinit var networkConnectivityObserver: NetworkConnectivityObserver


    override fun onCreate() {
        super.onCreate()

        // Initialize and observe network connectivity
        val connectivityFlow = networkConnectivityObserver.observe()

        // Collect and observe network connectivity status within a coroutine scope
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                connectivityFlow.collect { status ->
                    // Handle network status changes (e.g., enqueue your worker)
                    when (status) {
                        ConnectivityObserver.Status.Available -> {
                            // Handle network available status (enqueue worker, etc.)
                            enqueueDataSyncWorker()
                        }
                        // Handle other network statuses if needed
                        else -> {}
                    }
                }
            }
        }


    }

    private fun enqueueDataSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Create a one-time work request for your DbDataSyncWorker
        val syncRequest = OneTimeWorkRequest.Builder(DbDataSyncWorker::class.java)
            .setConstraints(constraints)
            .build()

        // Enqueue the work request to run immediately
        workManager.enqueue(syncRequest)
    }

}