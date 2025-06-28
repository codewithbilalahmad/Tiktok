package com.muhammad.core

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.datasource.okhttp.OkHttpDataSource
import okhttp3.OkHttpClient
import java.io.File

@UnstableApi
object VideoCache {
    private var simpleCache: SimpleCache? = null
    fun buildCacheDataSourceFactory(context: Context): CacheDataSource.Factory {
        if (simpleCache == null) {
            val cacheDir = File(context.cacheDir, "video_cache")
            val evictor = LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024)
            val database = StandaloneDatabaseProvider(context)
            simpleCache = SimpleCache(cacheDir, evictor, database)
        }
        val okHttpDataSource = OkHttpDataSource.Factory(OkHttpClient())
        return CacheDataSource.Factory().setCache(simpleCache!!).setUpstreamDataSourceFactory(
            DefaultDataSource.Factory(context, okHttpDataSource)
        ).setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }
}