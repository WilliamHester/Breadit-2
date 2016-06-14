package williamhester.me.breadit2;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
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

  public RedditApi(OkHttpClient client, JsonParser jsonParser) {
    mClient = client;
    mJsonParser = jsonParser;
  }

  public void getSubmissions() {

  }

  class RedditRequest {
    private static final String API_URL = "https://api.reddit.com/";
    private static final String OAUTH_URL = "https://oauth.reddit.com/";

    private String mPath;
    private boolean mAttemptedRefresh;
    private JsonCallback mCallback;

    RedditRequest(String path) {
      mPath = path;
    }

    public void getJson() {
      Request request = new Request.Builder()
          .url(getBaseUrl() + mPath)
          .headers(getHeaders())
          .build();

      mClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

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
