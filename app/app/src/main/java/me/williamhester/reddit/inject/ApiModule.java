package me.williamhester.reddit.inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.williamhester.reddit.apis.RedditClient;
import me.williamhester.reddit.apis.RedditHttpRequestFactory;
import me.williamhester.reddit.convert.RedditGsonConverter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import me.williamhester.reddit.models.Edited;
import me.williamhester.reddit.models.managers.AccountManager;

@Module
public class ApiModule {

  @Provides
  @Singleton
  Gson provideGson() {
    return new GsonBuilder()
        .registerTypeAdapter(Edited.class, new Edited.TypeAdapter())
        .create();
  }

  @Provides
  @Singleton
  JsonParser provideJsonParser() {
    return new JsonParser();
  }

  @Provides
  @Singleton
  AccountManager provideAccountManager() {
    return new AccountManager();
  }

  @Provides
  @Singleton
  RedditGsonConverter provideRedditGsonConverter(Gson gson) {
    return new RedditGsonConverter(gson);
  }
}
