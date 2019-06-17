package com.ok7apps.farmdropexample.data.network


import com.ok7apps.farmdropexample.core.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(factory: NetworkTransportInterceptorsFactory) =
            NetworkTransportFactory.createOkHttpClient(factory)

    @Provides
    fun networkIntercepterFactory()=
            NetworkTransportInterceptorsFactory()

    @Provides
    fun provideNetworkDatasource(
            producersApi: ProducersApi,
            schedulers: SchedulerProvider
    ): RemoteDataSource {
        return NetworkDataSourceImpl(producersApi, schedulers)
    }

}