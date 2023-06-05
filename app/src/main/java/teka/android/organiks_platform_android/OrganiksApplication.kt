package teka.android.organiks_platform_android

import android.app.Application
import teka.android.organiks_platform_android.di.OrganiksDI

class OrganiksApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        OrganiksDI.provideDb(this)
    }
}