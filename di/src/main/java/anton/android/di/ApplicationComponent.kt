package anton.android.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthModule::class])
interface ApplicationComponent {

}