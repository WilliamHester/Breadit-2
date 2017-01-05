package williamhester.me.breadit2.inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import williamhester.me.breadit2.models.Edited;
import williamhester.me.breadit2.models.managers.AccountManager;
import williamhester.me.breadit2.apis.RedditApi;

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
  AccountManager provideAccountManager() {
    return new AccountManager();
  }

  @Provides
  @Singleton
  RedditApi provideRedditApi(OkHttpClient client, JsonParser parser, Gson gson,
                             AccountManager accountManager) {
    return new RedditApi(client, parser, gson, accountManager);
  }
}
