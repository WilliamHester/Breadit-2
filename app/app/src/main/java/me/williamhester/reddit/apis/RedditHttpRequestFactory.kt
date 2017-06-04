package me.williamhester.reddit.apis

import android.util.Log

import com.google.gson.JsonElement
import com.google.gson.JsonParser

import org.greenrobot.eventbus.EventBus

import java.io.IOException

import me.williamhester.reddit.messages.FailedRedditRequestMessage
import me.williamhester.reddit.models.managers.AccountManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/** An HTTP request that  */
class RedditHttpRequestFactory(
    private val httpClient: OkHttpClient,
    private val accountManager: AccountManager,
    private val bus: EventBus,
    private val jsonParser: JsonParser
) {

  private val API_URL = "https://api.reddit.com/"
  private val OAUTH_URL = "https://oauth.reddit.com/"


  fun createRequest(path: String, converter: (JsonElement) -> Any) {
    createRequest(path, null, converter)
  }

  fun createRequest(path: String, queries: Map<String, String>?, converter: (JsonElement) -> Any) {
    val urlBuilder = HttpUrl.parse(baseUrl)
        .newBuilder()
        .addPathSegments(path)

    if (queries != null) {
      for (name in queries.keys) {
        urlBuilder.addQueryParameter(name, queries[name])
      }
    }

    val request = Request.Builder()
        .url(urlBuilder.build())
        .headers(headers)
        .build()

    httpClient.newCall(request).enqueue(JsonCallback(request, converter))
  }

  private val baseUrl: String
    get() = if (accountManager.isLoggedIn) OAUTH_URL else API_URL

  private val headers: Headers
    get() {
      val builder = Headers.Builder()
      if (accountManager.isLoggedIn) {
        builder.add("Authorization", "bearer " + accountManager.account!!.accessToken)
      }
      return builder.build()
    }

  private inner class JsonCallback constructor(
      private val request: Request, private val converter: (JsonElement) -> Any
  ) : Callback {

    private var hasAttemptedRefresh: Boolean = false

    override fun onFailure(call: Call, e: IOException) {
      bus.post(FailedRedditRequestMessage())
    }

    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
      if (!response.isSuccessful) {
        Log.d("RedditClient", response.toString())
      }
      if (response.code() == 401 && !hasAttemptedRefresh) {
        // TODO: Refresh the token
        hasAttemptedRefresh = true
      } else {
        try {
          Log.i("RedditHttpRequestFac", "Got response")
          val element = jsonParser.parse(response.body().charStream())
          bus.post(converter.invoke(element))
        } catch (e: Exception) {
          Log.d("RedditClient", "Request failed", e)
          bus.post(FailedRedditRequestMessage())
        }

      }
      response.close()
    }
  }
}
