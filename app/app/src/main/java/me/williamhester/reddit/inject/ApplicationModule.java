package me.williamhester.reddit.inject;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.williamhester.reddit.html.HtmlParser;
import me.williamhester.reddit.ui.ContentClickCallbacks;
import me.williamhester.reddit.ui.ContentClickCallbacksImpl;

/** A Module for injecting an {@link HtmlParser} and {@link ContentClickCallbacksImpl}. */
@Module
public class ApplicationModule {
  @Provides
  @Singleton
  ContentClickCallbacks provideVotableCallbacks(EventBus bus) {
    return new ContentClickCallbacksImpl(bus);
  }

  @Provides
  @Singleton
  HtmlParser provideHtmlParser(ContentClickCallbacks callbacks) {
    return new HtmlParser(callbacks);
  }
}
