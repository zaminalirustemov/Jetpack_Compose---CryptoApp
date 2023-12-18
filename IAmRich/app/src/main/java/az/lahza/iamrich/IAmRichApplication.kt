package az.lahza.iamrich

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IAmRichApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}