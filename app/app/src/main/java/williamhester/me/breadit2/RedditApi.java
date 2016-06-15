package williamhester.me.breadit2;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by william on 6/13/16.
 */
public class RedditApi {

  private OkHttpClient mClient;
  private JsonParser mJsonParser;
  private AccountManager mAccountManager;
  private Gson mGson;

  public RedditApi(OkHttpClient client, JsonParser jsonParser, Gson gson) {
    mClient = client;
    mJsonParser = jsonParser;
    mGson = gson;
  }

  public void getSubmissions(String place, String query, String after,
      DataCallback<List<Submission>> callback) {

  }

  class RedditRequest {
    private static final String API_URL = "https://api.reddit.com/";
    private static final String OAUTH_URL = "https://oauth.reddit.com/";

    private String mPath;
    private boolean mAttemptedRefresh;
    private JsonCallback mCallback;
    private HttpUrl mUrl;

    private RedditRequest() { }

    static RedditRequest get(String path, Map<String, String> queries) {
      HttpUrl.Builder urlBuilder = HttpUrl.parse(getBaseUrl())
          .newBuilder()
          .addPathSegments(mPath);
      return null;
    }

    RedditRequest() {
    }

    public void getJson() {

      for ()

      Request request = new Request.Builder()
          .url(url)
          .headers(getHeaders())
          .method(mMethod, RequestBody)
          .build();

      mClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          mCallback.onJsonResponse(null);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
          if (!response.isSuccessful()) {
            Log.d("RedditApi", response.toString());
          }
          if (response.code() == 401 && !mAttemptedRefresh) {
            // TODO: Refresh the token
            mAttemptedRefresh = true;
          } else {
            try {
              mCallback.onJsonResponse(mJsonParser.parse(response.body().charStream()));
            } catch (JsonIOException | JsonSyntaxException e) {
              Log.d("RedditApi", "Request failed", e);
            }
            mCallback.onJsonResponse(null);
          }
        }
      });
    }

    private String getBaseUrl() {
      return mAccountManager.isLoggedIn() ? OAUTH_URL : API_URL;
    }

    private Headers getHeaders() {
      Headers.Builder builder = new Headers.Builder();
      if (mAccountManager.isLoggedIn()) {
        builder.add("Authorization", "bearer " + mAccountManager.getAccount().getAccessToken());
      }
      return builder.build();
    }
  }

  interface JsonCallback {
    void onJsonResponse(JsonElement element);
  }

}
