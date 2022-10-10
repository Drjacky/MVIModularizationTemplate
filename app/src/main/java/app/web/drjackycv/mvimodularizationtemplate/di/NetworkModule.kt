package app.web.drjackycv.mvimodularizationtemplate.di

import android.content.Context
import app.web.drjackycv.core.designsystem.allowWrites
import app.web.drjackycv.core.network.BaseHttpClient
import app.web.drjackycv.core.network.BuildConfig
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

private const val MAX_SIZE_BYTE = 10 * 1024 * 1024

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    // Creates a 10MB MemoryCacheFactory
    private val cacheFactory = MemoryCacheFactory(maxSizeBytes = MAX_SIZE_BYTE)

    @Provides
    @Singleton
    fun okHttpClient(baseHttpClient: BaseHttpClient): OkHttpClient = baseHttpClient.okHttpClient

    @Provides
    @Singleton
    fun chuckerCollector(@ApplicationContext appContext: Context): ChuckerCollector =
        allowWrites {
            ChuckerCollector(
                context = appContext,
                showNotification = true,
                retentionPeriod = RetentionManager.Period.ONE_HOUR
            )
        }

    @Provides
    @Singleton
    fun apolloGraphql(okHttpClient: OkHttpClient): ApolloClient =
        ApolloClient.Builder()
            .okHttpClient(okHttpClient)
            .serverUrl(BuildConfig.BASE_URL)
            .normalizedCache(cacheFactory)
            .build()

}