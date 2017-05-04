package me.williamhester.reddit.apis;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.williamhester.reddit.convert.ConverterException;
import me.williamhester.reddit.convert.RedditGsonConverter;
import me.williamhester.reddit.models.Post;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import me.williamhester.reddit.models.Comment;
import me.williamhester.reddit.models.MoreComment;
import me.williamhester.reddit.models.MoreCommentJson;
import me.williamhester.reddit.models.Submission;
import me.williamhester.reddit.models.SubmissionJson;
import me.williamhester.reddit.models.TextComment;
import me.williamhester.reddit.models.TextCommentJson;
import me.williamhester.reddit.models.managers.AccountManager;

/** Contains methods to communicate with Reddit */
public class RedditClient {

  private final OkHttpClient httpClient;
  private final JsonParser jsonParser;
  private final AccountManager accountManager;
  private final RedditGsonConverter redditGsonConverter;

  public RedditClient(
      OkHttpClient client,
      JsonParser jsonParser,
      AccountManager accountManager,
      RedditGsonConverter redditGsonConverter) {
    this.httpClient = client;
    this.jsonParser = jsonParser;
    this.accountManager = accountManager;
    this.redditGsonConverter = redditGsonConverter;
  }

  public void getSubmissions(String place, String query, String after,
                             final DataCallback<List<Submission>> callback) {
    Map<String, String> queries = new HashMap<>();
    if (after != null) {
      queries.put("after", after);
    }
    new RedditRequest(place, queries).getJson(new JsonCallback() {
      @Override
      public void onJsonResponse(JsonElement element) {
        if (element == null) {
          return;
        }
        final List<Submission> submissions = new ArrayList<>();
        try {
          submissions.addAll(redditGsonConverter.<Submission>toList(element));
        } catch (ConverterException e) {
          Log.e("RedditClient", "Failed to convert json", e);
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
            callback.onResponse(submissions);
          }
        });
      }
    });
  }

  public void getComments(String permalink,
                          final DataCallback<Pair<Submission, List<Comment>>> callback) {
    new RedditRequest(permalink, null).getJson(new JsonCallback() {
      @Override
      public void onJsonResponse(final JsonElement element) {
        if (element == null) {
          return;
        }
        Post post = null;
        try {
          post = redditGsonConverter.toPost(element);
        } catch (ConverterException e) {
          Log.e("RedditClient", "failed to convert json", e);
        }
        final Post post1 = post;
        if (post == null) {
          return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
            callback.onResponse(new Pair<>(post1.getSubmission(), post1.getComments()));
          }
        });
      }
    });
  }

  class RedditRequest {
    private static final String API_URL = "https://api.reddit.com/";
    private static final String OAUTH_URL = "https://oauth.reddit.com/";

    private boolean attemptedRefresh;
    private Request request;

    RedditRequest(String path, Map<String, String> queries) {
      HttpUrl.Builder urlBuilder = HttpUrl.parse(getBaseUrl())
          .newBuilder()
          .addPathSegments(path);

      if (queries != null) {
        for (String name : queries.keySet()) {
          urlBuilder.addQueryParameter(name, queries.get(name));
        }
      }

      request = new Request.Builder()
          .url(urlBuilder.build())
          .headers(getHeaders())
          .build();
    }

    public void getJson(final JsonCallback callback) {
      httpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          callback.onJsonResponse(null);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
          if (!response.isSuccessful()) {
            Log.d("RedditClient", response.toString());
          }
          if (response.code() == 401 && !attemptedRefresh) {
            // TODO: Refresh the token
            attemptedRefresh = true;
          } else {
            try {
              callback.onJsonResponse(jsonParser.parse(response.body().charStream()));
            } catch (JsonIOException | JsonSyntaxException e) {
              Log.d("RedditClient", "Request failed", e);
              callback.onJsonResponse(null);
            }
          }
          response.close();
        }
      });
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
  }

  interface JsonCallback {
    void onJsonResponse(JsonElement element);
  }
}
