package me.williamhester.reddit.inject

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

import org.greenrobot.eventbus.EventBus

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import me.williamhester.reddit.apis.RedditClient
import me.williamhester.reddit.apis.RedditHttpRequestFactory
import me.williamhester.reddit.convert.RedditGsonConverter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import me.williamhester.reddit.models.Edited
import me.williamhester.reddit.models.managers.AccountManager

@Module
class ApiModule {
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
  internal fun provideAccountManager(): AccountManager {
    return AccountManager()
  }

  @Provides
  @Singleton
  internal fun provideRedditGsonConverter(gson: Gson): RedditGsonConverter {
    return RedditGsonConverter(gson)
  }

  @Provides
  @Singleton
  internal fun provideHttpRedditRequestFactory(
      client: OkHttpClient,
      bus: EventBus,
      accountManager: AccountManager,
      parser: JsonParser): RedditHttpRequestFactory {
    return RedditHttpRequestFactory(client, accountManager, bus, parser)
  }

  @Provides
  @Singleton
  internal fun provideRedditApi(
      converter: RedditGsonConverter,
      factory: RedditHttpRequestFactory): RedditClient {
    return RedditClient(converter, factory)
  }
}
