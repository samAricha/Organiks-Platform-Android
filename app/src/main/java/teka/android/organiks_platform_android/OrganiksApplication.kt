package teka.android.organiks_platform_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import teka.android.organiks_platform_android.di.OrganiksDI

@HiltAndroidApp
class OrganiksApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        OrganiksDI.provideDb(this)
    }
}