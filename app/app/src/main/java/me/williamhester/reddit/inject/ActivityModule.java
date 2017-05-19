package me.williamhester.reddit.inject;

import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.williamhester.reddit.apis.RedditClient;
import me.williamhester.reddit.apis.RedditHttpRequestFactory;
import me.williamhester.reddit.convert.RedditGsonConverter;
import me.williamhester.reddit.models.managers.AccountManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/** A module for creating Activity-wide instances of classes */
@Module
public class ActivityModule {
  @Provides
  @Singleton
  EventBus provideBus() {
    return new EventBus();
  }

  @Provides
  @Singleton
  OkHttpClient provideOkClient() {
    Interceptor headerInterceptor = new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Customize the request
        Request.Builder builder = original.newBuilder()
            .addHeader("User-Agent", "Breadit-2")
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .method(original.method(), original.body());

        return chain.proceed(builder.build());
      }
    };

    return new OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .build();
  }

  @Provides
  @Singleton
  RedditHttpRequestFactory provideHttpRedditRequestFactory(
      OkHttpClient client,
      EventBus bus,
      AccountManager accountManager,
      JsonParser parser) {
    return new RedditHttpRequestFactory(client, accountManager, bus, parser);
  }

  @Provides
  @Singleton
  RedditClient provideRedditApi(RedditGsonConverter converter, RedditHttpRequestFactory factory) {
    return new RedditClient(converter, factory);
  }
}
