package me.williamhester.reddit.inject

import org.greenrobot.eventbus.EventBus

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.ui.ContentClickCallbacks
import me.williamhester.reddit.ui.ContentClickCallbacksImpl

/** A Module for injecting an [HtmlParser] and [ContentClickCallbacksImpl].  */
@Module
class ApplicationModule {
  @Provides
  @Singleton
  internal fun provideBus(): EventBus {
    return EventBus()
  }

  @Provides
  @Singleton
  internal fun provideVotableCallbacks(bus: EventBus): ContentClickCallbacks {
    return ContentClickCallbacksImpl(bus)
  }

  @Provides
  @Singleton
  internal fun provideHtmlParser(callbacks: ContentClickCallbacks): HtmlParser {
    return HtmlParser(callbacks)
  }
}
