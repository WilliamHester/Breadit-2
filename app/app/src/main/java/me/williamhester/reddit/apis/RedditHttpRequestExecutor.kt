package me.williamhester.reddit.apis

import android.util.Base64
import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.realm.Realm
import me.williamhester.reddit.BuildConfig
import me.williamhester.reddit.convert.RedditGsonConverter
import me.williamhester.reddit.models.Account
import me.williamhester.reddit.models.managers.AccountManager
import okhttp3.*
import java.io.IOException

/** An HTTP request that  */
class RedditHttpRequestExecutor(
    private val httpClient: OkHttpClient,
    private val accountManager: AccountManager,
    private val jsonParser: JsonParser,
    private val redditGsonConverter: RedditGsonConverter
) {

  private fun getBaseUrl(accessToken: String?): String {
    return if (accessToken != null) OAUTH_URL else API_URL
  }

  private fun getHeaders(accessToken: String?): Headers {
    val builder = Headers.Builder()
    if (accessToken != null) {
      builder.add("Authorization", "bearer $accessToken")
    } else {
      val encoded = Base64.encodeToString("${BuildConfig.REDDIT_CLIENT_ID}:".toByteArray(), 0).trim()
      builder.add("Authorization", "Basic $encoded")
    }
    return builder.build()
  }

  internal fun execute(request: RedditHttpRequest, hasAttemptedRefresh: Boolean = false) {
    val urlBuilder = HttpUrl.parse(getBaseUrl(request.accessToken))!!
        .newBuilder()
        .addPathSegments(request.path)

    request.queries?.forEach { urlBuilder.addQueryParameter(it.key, it.value) }

    val method = if (request.params != null) POST else GET
    val requestBody: RequestBody? =
        if (method != GET) {
          val requestBodyBuilder = FormBody.Builder()
          request.params?.forEach { requestBodyBuilder.add(it.key, it.value) }
          requestBodyBuilder.build()
        } else {
          null
        }

    val httpRequest = Request.Builder()
        .url(urlBuilder.build())
        .method(method, requestBody)
        .headers(getHeaders(request.accessToken))
        .build()

    Log.d("RedditRequestFactory", httpRequest.toString())

    httpClient.newCall(httpRequest).enqueue(JsonCallback(request, hasAttemptedRefresh))
  }

  private fun refreshToken() {
    val urlBuilder = HttpUrl.parse(getBaseUrl(null))!!
        .newBuilder()
        .addPathSegments("api/v1/access_token")

    val params = mapOf(
        "grant_type" to "refresh_token",
        "refresh_token" to accountManager.refreshToken)

    val requestBody = FormBody.Builder()
    params.forEach { requestBody.add(it.key, it.value) }

    val request = Request.Builder()
        .url(urlBuilder.build())
        .method(POST, requestBody.build())
        .headers(getHeaders(null))
        .build()

    Log.d("RedditRequestFactory", request.toString())

    // This is the lazy way to do it, not returning anything.
    // TODO actually determine if something useful can be returned from this
    val response = httpClient.newCall(request).execute()
    if (response.isSuccessful) {
      Log.d("RedditRequestFactory", "Got a new access token!")
      val accessTokenJson =
          redditGsonConverter.toAccessTokenJson(jsonParser.parse(response.body()!!.charStream()))
      Realm.getDefaultInstance().executeTransaction {
        val account =
            it
                .where(Account::class.java)
                .equalTo("username", accountManager.username)
                .findFirst()!!
        account.accessToken = accessTokenJson.accessToken
        accountManager.setAccount(account)
      }
    } else {
      Log.d("RedditRequestFactory", "Failed to get a new access token. :(")
    }
  }

  private inner class JsonCallback(
      private val request: RedditHttpRequest,
      private val hasAttemptedRefresh: Boolean
  ) : Callback {

    override fun onFailure(call: Call, e: IOException) {
      request.callback.onFailure(e)
    }

    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
      if (!response.isSuccessful) {
        Log.d("RedditClient", response.toString())
      }
      if (response.code() == 401 && !hasAttemptedRefresh && accountManager.isLoggedIn) {
        // TODO: Refresh the token
        Log.d("RedditRequestFactory", "Requesting new access token")
        refreshToken()
        execute(request.toBuilder().accessToken(accountManager.accessToken).build(), true)
      } else {
        try {
          Log.i("RedditHttpRequestFac", "Got response")
          val element = jsonParser.parse(response.body()!!.charStream())
          request.callback.onResponse(element)
        } catch (e: Exception) {
          Log.d("RedditClient", "Request failed", e)
          request.callback.onFailure(e)
        }
      }
      response.close()
    }
  }

  interface JsonResponseCallback {
    fun onFailure(e: Exception)
    fun onResponse(element: JsonElement)
  }

  companion object {
    private const val POST = "POST"
    private const val GET = "GET"
    private const val API_URL = "https://api.reddit.com/"
    private const val OAUTH_URL = "https://oauth.reddit.com/"
  }
}
