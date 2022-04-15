package app.web.drjackycv.mvimodularizationtemplate.di

import app.web.drjackycv.core.network.BaseHttpClient
import app.web.drjackycv.core.network.BuildConfig
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun okHttpClient(baseHttpClient: BaseHttpClient): OkHttpClient = baseHttpClient.okHttpClient

    @Provides
    @Singleton
    fun apolloGraphql(okHttpClient: OkHttpClient): ApolloClient =
        ApolloClient.Builder().okHttpClient(okHttpClient).serverUrl(BuildConfig.BASE_URL)
            .build()

}