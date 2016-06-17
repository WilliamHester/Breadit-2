package williamhester.me.breadit2.apis;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import williamhester.me.breadit2.models.Comment;
import williamhester.me.breadit2.models.MoreComment;
import williamhester.me.breadit2.models.MoreCommentJson;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.models.SubmissionJson;
import williamhester.me.breadit2.models.TextComment;
import williamhester.me.breadit2.models.TextCommentJson;
import williamhester.me.breadit2.models.managers.AccountManager;

/**
 * Created by william on 6/13/16.
 */
public class RedditApi {

  private OkHttpClient httpClient;
  private JsonParser jsonParser;
  private AccountManager accountManager;
  private Gson gson;

  public RedditApi(OkHttpClient client, JsonParser jsonParser, Gson gson,
                   AccountManager accountManager) {
    httpClient = client;
    this.jsonParser = jsonParser;
    this.accountManager = accountManager;
    this.gson = gson;
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
        JsonObject object = element.getAsJsonObject();
        final List<Submission> submissions = new ArrayList<>();
        if (object.has("data") && object.get("data").getAsJsonObject().has("children")) {
          JsonArray array = object.get("data").getAsJsonObject().get("children").getAsJsonArray();
          for (JsonElement elem : array) {
            SubmissionJson json = gson.fromJson(elem.getAsJsonObject().get("data"),
                SubmissionJson.class);
            submissions.add(new Submission(json));
          }
        } else {
          Log.d("RedditApi", "no data available");
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
      public void onJsonResponse(JsonElement element) {
        if (element == null) {
          return;
        }
        JsonArray array = element.getAsJsonArray();
        JsonObject submissionData = array.get(0).getAsJsonObject()
            .get("data").getAsJsonObject()
            .get("children").getAsJsonArray()
            .get(0).getAsJsonObject()
            .get("data").getAsJsonObject();
        final Submission submission =
            new Submission(gson.fromJson(submissionData, SubmissionJson.class));

        JsonArray commentsJson = array.get(1).getAsJsonObject()
            .get("data").getAsJsonObject()
            .get("children").getAsJsonArray();
        final List<Comment> comments = parseCommentJson(commentsJson, 0);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
            callback.onResponse(new Pair<>(submission, comments));
          }
        });
      }
    });
  }

  private List<Comment> parseCommentJson(JsonArray commentsJson, int level) {
    List<Comment> comments = new ArrayList<>();
    for (JsonElement commentElement : commentsJson) {
      Comment comment;
      JsonObject jsonObject = commentElement.getAsJsonObject();
      if (jsonObject.get("kind").getAsString().equals("more")) {
        comment = new MoreComment(
            gson.fromJson(jsonObject.get("data"), MoreCommentJson.class),
            level);
        comments.add(comment);
      } else {
        comment = new TextComment(
            gson.fromJson(jsonObject.get("data"), TextCommentJson.class),
            level);
        JsonObject commentJson = jsonObject.get("data").getAsJsonObject();
        JsonElement replies = commentJson.get("replies");
        comments.add(comment);
        if (replies.isJsonObject()) {
          JsonArray repliesArray = replies.getAsJsonObject()
              .get("data").getAsJsonObject()
              .get("children").getAsJsonArray();
          comments.addAll(parseCommentJson(repliesArray, level + 1));
        }
      }
    }
    return comments;
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
            Log.d("RedditApi", response.toString());
          }
          if (response.code() == 401 && !attemptedRefresh) {
            // TODO: Refresh the token
            attemptedRefresh = true;
          } else {
            try {
              callback.onJsonResponse(jsonParser.parse(response.body().charStream()));
            } catch (JsonIOException | JsonSyntaxException e) {
              Log.d("RedditApi", "Request failed", e);
            }
            callback.onJsonResponse(null);
          }
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
