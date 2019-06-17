package com.ok7apps.farmdropexample.di


import com.ok7apps.farmdropexample.FarmDropApp
import com.ok7apps.farmdropexample.data.DataModule
import com.ok7apps.farmdropexample.data.network.NetworkModule
import com.ok7apps.farmdropexample.domain.DomainModule
import com.ok7apps.farmdropexample.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            DataModule::class,
            NetworkModule::class,
            DomainModule::class,
            MainActivityModule::class,
            AndroidInjectionModule::class,
            ActivityBuilder::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: FarmDropApp): Builder

        fun build(): AppComponent

    }

    fun inject(app: FarmDropApp)
}