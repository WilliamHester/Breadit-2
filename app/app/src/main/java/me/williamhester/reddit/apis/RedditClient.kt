package me.williamhester.reddit.apis

import android.util.Log
import com.google.gson.JsonElement
import me.williamhester.reddit.BuildConfig
import me.williamhester.reddit.convert.RedditGsonConverter
import me.williamhester.reddit.messages.FailedRedditRequestMessage
import me.williamhester.reddit.messages.LogInFinishedMessage
import me.williamhester.reddit.messages.PostMessage
import me.williamhester.reddit.messages.VotableListMessage
import me.williamhester.reddit.models.Account
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.models.Subreddit
import me.williamhester.reddit.models.managers.AccountManager
import org.greenrobot.eventbus.EventBus
import java.util.*

/** Contains methods to communicate with Reddit  */
class RedditClient(
    private val redditGsonConverter: RedditGsonConverter,
    private val requestExecutor: RedditHttpRequestExecutor,
    private val bus: EventBus,
    private val accountManager: AccountManager
) {

  fun getSubmissions(place: String, query: String?, after: String?) {
    val queries = HashMap<String, String>()
    if (after != null) {
      queries.put("after", after)
    }
    val callback = PostingCallback {
      VotableListMessage(redditGsonConverter.toList<Submission>(it))
    }
    val request = RedditHttpRequest.Builder(place, callback)
        .accessToken(accountManager.accessToken)
        .queries(queries)
        .build()
    requestExecutor.createRequest(request)
  }

  fun getComments(permalink: String) {
    val callback = PostingCallback { PostMessage(redditGsonConverter.toPost(it)) }
    val request =
        RedditHttpRequest.Builder(permalink, callback)
            .accessToken(accountManager.accessToken)
            .build()
    requestExecutor.createRequest(request)
  }

  fun getMySubreddits() {
    getMySubreddits(null)
  }

  private fun getMySubreddits(after: String?, subreddits: List<Subreddit> = ArrayList()) {
    var subredditsList = subreddits
    val innerCallback = object : RedditHttpRequestExecutor.JsonResponseCallback {
      override fun onFailure(e: Exception) {
        bus.post(FailedRedditRequestMessage())
        Log.e("RedditClient", "Failed to communicate to Reddit", e)
      }

      override fun onResponse(element: JsonElement) {
        val subList = redditGsonConverter.toList<Subreddit>(element)
        subredditsList += subList
        if (subList.size == 25) {
          getMySubreddits(subList.last().name, subredditsList)
        } else {
          bus.post(subredditsList)
        }
      }
    }

    val queries = if (after != null) {
      mapOf("after" to after)
    } else {
      null
    }

    val request = RedditHttpRequest.Builder("subreddits/mine", innerCallback)
        .accessToken(accountManager.accessToken)
        .queries(queries)
        .build()
    requestExecutor.createRequest(request)
  }

  fun logIn(code: String) {
    val callback = PostingCallback { redditGsonConverter.toAccessTokenJson(it) }
    val request =
        RedditHttpRequest.Builder("api/v1/access_token", callback)
            .params(mapOf(
                "grant_type" to "authorization_code",
                "code" to code,
                "redirect_uri" to BuildConfig.REDDIT_REDIRECT_URI))
            .build()

    requestExecutor.createRequest(request)
  }

  fun getMe(account: Account) {
    val callback = PostingCallback {
      val meResponse = redditGsonConverter.toMeResponse(it)
      account.username = meResponse.name
      LogInFinishedMessage(account)
    }
    val request = RedditHttpRequest.Builder("api/v1/me", callback)
        .accessToken(account.accessToken)
        .build()
    requestExecutor.createRequest(request)
  }

  private inner class PostingCallback(
      private val converter: (JsonElement) -> Any
  ) : RedditHttpRequestExecutor.JsonResponseCallback {

    override fun onFailure(e: Exception) {
      // Tell the user we had an error communicating with the network
      bus.post(FailedRedditRequestMessage())
      Log.e("RedditClient", "Failed to communicate to Reddit", e)
    }

    override fun onResponse(element: JsonElement) {
      bus.post(converter.invoke(element))
    }
  }
}
