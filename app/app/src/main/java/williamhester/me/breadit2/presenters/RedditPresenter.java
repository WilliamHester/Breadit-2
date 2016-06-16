package williamhester.me.breadit2.presenters;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import williamhester.me.breadit2.models.managers.AccountManager;
import williamhester.me.breadit2.apis.RedditApi;

/**
 * Created by william on 6/15/16.
 */
public abstract class RedditPresenter {

  protected RedditApi mRedditApi = new RedditApi(new OkHttpClient(), new JsonParser(), new Gson(), new AccountManager());

}
