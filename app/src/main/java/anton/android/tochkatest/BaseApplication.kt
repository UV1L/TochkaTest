package anton.android.tochkatest

import android.app.Application
import anton.android.tochkatest.di.ApplicationComponent
import anton.android.tochkatest.di.DaggerApplicationComponent
import anton.android.tochkatest.utils.ApplicationState
import timber.log.Timber

class BaseApplication : Application() {

    val applicationState = ApplicationState
    lateinit var daggerComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        daggerComponent = DaggerApplicationComponent.factory().create(this)
        Timber.plant(Timber.DebugTree())
    }
}