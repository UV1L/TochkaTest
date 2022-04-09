package anton.android.tochkatest

import android.app.Application
import anton.android.di.ApplicationComponent
import anton.android.di.DaggerApplicationComponent

class BaseApplication : Application() {

    lateinit var daggerComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        daggerComponent = DaggerApplicationComponent.factory().create(this)
    }
}