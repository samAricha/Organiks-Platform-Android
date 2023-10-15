package teka.android.organiks_platform_android

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import teka.android.organiks_platform_android.workmanager.DbDataSyncWorker
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class OrganiksApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Define the repeat interval and unit (e.g., every 24 hours)
        val repeatInterval = 24L
        val repeatIntervalTimeUnit = TimeUnit.HOURS

        // Create a periodic work request
        val syncRequest = PeriodicWorkRequest.Builder(
            DbDataSyncWorker::class.java,
            repeatInterval,
            repeatIntervalTimeUnit
        ).build()

        // Enqueue the work request
        WorkManager.getInstance(applicationContext).enqueue(syncRequest)
    }

}