package williamhester.me.breadit2.inject;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import williamhester.me.breadit2.html.HtmlParser;
import williamhester.me.breadit2.ui.ContentClickCallbacks;
import williamhester.me.breadit2.ui.ContentClickCallbacksImpl;

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
