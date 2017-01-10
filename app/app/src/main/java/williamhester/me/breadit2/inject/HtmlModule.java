package williamhester.me.breadit2.inject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import williamhester.me.breadit2.html.HtmlParser;
import williamhester.me.breadit2.ui.ContentClickCallbacks;
import williamhester.me.breadit2.ui.ContentClickCallbacksImpl;

/** A Module for injecting an {@link HtmlParser} and {@link ContentClickCallbacksImpl}. */
@Module
public class HtmlModule {
  private Context context;

  /** Creates an HtmlModule with a context. */
  public HtmlModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  HtmlParser provideHtmlParser(ContentClickCallbacks callbacks) {
    return new HtmlParser(callbacks);
  }

  @Provides
  @Singleton
  Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  ContentClickCallbacks provideVotableCallbacks() {
    return new ContentClickCallbacksImpl(context);
  }
}
