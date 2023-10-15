package teka.android.organiks_platform_android.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.networking.ConnectivityObserver
import teka.android.organiks_platform_android.workmanager.DbDataSyncWorker

class NetworkConnectivityObserver(
    private val context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(ConnectivityObserver.Status.Available)
                        enqueueDataSyncWorker()

                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.Status.Losing) }

                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.Status.Lost) }

                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.Unavailable) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose{
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

    private fun enqueueDataSyncWorker() {
        // Create a one-time work request for your DbDataSyncWorker
        val syncRequest = OneTimeWorkRequest.Builder(DbDataSyncWorker::class.java)
            .build()

        // Enqueue the work request to run immediately
        WorkManager.getInstance(context).enqueue(syncRequest)
    }
}