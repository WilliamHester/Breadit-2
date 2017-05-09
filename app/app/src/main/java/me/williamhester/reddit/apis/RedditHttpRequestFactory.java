package me.williamhester.reddit.apis;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import me.williamhester.reddit.messages.FailedRedditRequestMessage;
import me.williamhester.reddit.models.managers.AccountManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/** An HTTP request that */
public class RedditHttpRequestFactory {
  private static final String API_URL = "https://api.reddit.com/";
  private static final String OAUTH_URL = "https://oauth.reddit.com/";

  private final OkHttpClient httpClient;
  private final AccountManager accountManager;
  private final EventBus bus;
  private final JsonParser jsonParser;

  public RedditHttpRequestFactory(
      OkHttpClient client, AccountManager accountManager, EventBus bus, JsonParser jsonParser) {
    this.httpClient = client;
    this.accountManager = accountManager;
    this.bus = bus;
    this.jsonParser = jsonParser;
  }

  public void createRequest(String path, Converter converter) {
    createRequest(path, null, converter);
  }

  public void createRequest(String path, Map<String, String> queries, Converter converter) {
    HttpUrl.Builder urlBuilder = HttpUrl.parse(getBaseUrl())
        .newBuilder()
        .addPathSegments(path);

    if (queries != null) {
      for (String name : queries.keySet()) {
        urlBuilder.addQueryParameter(name, queries.get(name));
      }
    }

    Request request = new Request.Builder()
        .url(urlBuilder.build())
        .headers(getHeaders())
        .build();

    httpClient.newCall(request).enqueue(new JsonCallback(request, converter));
  }

  private String getBaseUrl() {
    return accountManager.isLoggedIn() ? OAUTH_URL : API_URL;
  }

  private Headers getHeaders() {
    Headers.Builder builder = new Headers.Builder();
    if (accountManager.isLoggedIn()) {
      builder.add("Authorization", "bearer " + accountManager.getAccount().getAccessToken());
    }
    return builder.build();
  }

  private class JsonCallback implements Callback {
    private boolean hasAttemptedRefresh;
    private final Converter converter;
    private final Request request;

    private JsonCallback(Request request, Converter converter) {
      this.converter = converter;
      this.request = request;
    }

    @Override
    public void onFailure(Call call, IOException e) {
      bus.post(new FailedRedditRequestMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
      if (!response.isSuccessful()) {
        Log.d("RedditClient", response.toString());
      }
      if (response.code() == 401 && !hasAttemptedRefresh) {
        // TODO: Refresh the token
        hasAttemptedRefresh = true;
      } else {
        try {
          Log.i("RedditHttpRequestFac", "Got response");
          JsonElement element = jsonParser.parse(response.body().charStream());
          bus.post(converter.convert(element));
        } catch (Exception e) {
          Log.d("RedditClient", "Request failed", e);
          bus.post(new FailedRedditRequestMessage());
        }
      }
      response.close();
    }
  }

  /**
   * An interface for converting {@link JsonElement}s into a type to emit via the {@link EventBus}.
   */
  interface Converter {
    Object convert(JsonElement element) throws Exception;
  }
}
