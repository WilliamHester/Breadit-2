package me.williamhester.reddit.inject

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import dagger.Module
import dagger.Provides
import me.williamhester.reddit.convert.RedditGsonConverter
import me.williamhester.reddit.models.Edited
import me.williamhester.reddit.models.managers.AccountManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * A Module for providing dependencies that will be reused throughout the applicaiton's lifetime.
 */
@Module
class ApplicationModule(private val context: Context) {

  @Provides
  @Singleton
  internal fun provideGson(): Gson {
    return GsonBuilder()
        .registerTypeAdapter(Edited::class.java, Edited.TypeAdapter())
        .create()
  }

  @Provides
  @Singleton
  internal fun provideJsonParser(): JsonParser {
    return JsonParser()
  }

  @Provides
  @Singleton
  internal fun provideOkClient(): OkHttpClient {
    val headerInterceptor = Interceptor { chain ->
      val original = chain.request()

      // Customize the request
      val builder = original.newBuilder()
          .addHeader("User-Agent", "Breadit-2")
          .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
          .method(original.method(), original.body())

      chain.proceed(builder.build())
    }

    return OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .build()
  }

  @Provides
  @Singleton
  internal fun provideContext(): Context = context

  @Provides
  @Singleton
  internal fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
  }

  @Provides
  @Singleton
  internal fun provideAccountManager(sharedPreferences: SharedPreferences): AccountManager {
    return AccountManager(sharedPreferences)
  }

  @Provides
  @Singleton
  internal fun provideRedditGsonConverter(gson: Gson): RedditGsonConverter {
    return RedditGsonConverter(gson)
  }
}
