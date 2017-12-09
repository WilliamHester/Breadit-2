package me.williamhester.reddit.inject

import com.google.gson.JsonParser
import dagger.Module
import dagger.Provides
import me.williamhester.reddit.apis.RedditClient
import me.williamhester.reddit.apis.RedditHttpRequestExecutor
import me.williamhester.reddit.convert.RedditGsonConverter
import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.models.managers.AccountManager
import me.williamhester.reddit.ui.ContentClickCallbacks
import me.williamhester.reddit.ui.ContentClickCallbacksImpl
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus

/**
 * A module that defines all of the dependencies that should live as long as an Activity is alive.
 */
@Module
class ActivityModule {
  @Provides
  @ActivityScope
  internal fun provideBus(): EventBus {
    return EventBus()
  }

  @Provides
  @ActivityScope
  internal fun provideHttpRedditRequestFactory(
      client: OkHttpClient,
      bus: EventBus,
      accountManager: AccountManager,
      parser: JsonParser,
      redditGsonConverter: RedditGsonConverter): RedditHttpRequestExecutor {
    return RedditHttpRequestExecutor(client, accountManager, parser, redditGsonConverter)
  }

  @Provides
  @ActivityScope
  internal fun provideRedditApi(
      converter: RedditGsonConverter,
      executor: RedditHttpRequestExecutor,
      bus: EventBus,
      accountManager: AccountManager): RedditClient {
    return RedditClient(converter, executor, bus, accountManager)
  }

  @Provides
  @ActivityScope
  internal fun provideVotableCallbacks(bus: EventBus): ContentClickCallbacks {
    return ContentClickCallbacksImpl(bus)
  }

  @Provides
  @ActivityScope
  internal fun provideHtmlParser(callbacks: ContentClickCallbacks): HtmlParser {
    return HtmlParser(callbacks)
  }
}