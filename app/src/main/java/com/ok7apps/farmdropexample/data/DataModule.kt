package com.ok7apps.farmdropexample.data

import android.content.Context
import com.ok7apps.farmdropexample.BuildConfig
import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.data.local.ApplicationDataBase
import com.ok7apps.farmdropexample.data.local.LocalDataSource
import com.ok7apps.farmdropexample.data.local.LocalDataSourceImpl
import com.ok7apps.farmdropexample.data.model.producer.ProducerRepositoryImpl
import com.ok7apps.farmdropexample.data.network.ProducersApi
import com.ok7apps.farmdropexample.data.network.RemoteDataSource
import com.ok7apps.farmdropexample.data.network.RetrofitApiFactory
import com.ok7apps.farmdropexample.domain.producer.ProducerRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideProducersApi(okHttpClient: OkHttpClient): ProducersApi {
        return RetrofitApiFactory.create(
                ProducersApi::class.java,
                BuildConfig.BASE_API_URL,
                okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ApplicationDataBase {
        return ApplicationDataBase.newInstance(context)
    }

    @Provides
    @Singleton
    fun provideLocalDatasource(
            applicationDataBase: ApplicationDataBase,
            schedulers: SchedulerProvider
    ): LocalDataSource {
        return LocalDataSourceImpl(applicationDataBase, schedulers)
    }

    @Provides
    @Singleton
    fun provideProducersRepository(
            networkDatasource: RemoteDataSource,
            localDatasource: LocalDataSource
    ): ProducerRepository {
        return ProducerRepositoryImpl(networkDatasource, localDatasource)
    }

}