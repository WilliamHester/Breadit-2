package me.williamhester.reddit.inject;

import com.squareup.otto.Bus;

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
  Bus provideBus() {
    return new Bus();
  }

  @Provides
  @Singleton
  ContentClickCallbacks provideVotableCallbacks(Bus bus) {
    return new ContentClickCallbacksImpl(bus);
  }

  @Provides
  @Singleton
  HtmlParser provideHtmlParser(ContentClickCallbacks callbacks) {
    return new HtmlParser(callbacks);
  }
}
