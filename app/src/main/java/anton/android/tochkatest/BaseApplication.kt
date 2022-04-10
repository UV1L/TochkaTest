package anton.android.tochkatest

import android.app.Application
import anton.android.di.ApplicationComponent
import anton.android.di.DaggerApplicationComponent
import anton.android.tochkatest.utils.ApplicationState

class BaseApplication : Application() {

    val applicationState = ApplicationState
    lateinit var daggerComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        daggerComponent = DaggerApplicationComponent.factory().create(this)
    }
}