package me.williamhester.reddit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import me.williamhester.reddit.BuildConfig
import me.williamhester.reddit.R
import me.williamhester.reddit.ui.activities.LogInActivity

/**
 * Created by williamhester on 11/14/17.
 */

class LogInWebViewFragment : BaseFragment() {

  companion object {
    fun newInstance() = LogInWebViewFragment()
  }

  lateinit var webView: WebView

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?): View? =
    inflater.inflate(R.layout.fragment_log_in, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    webView = view.findViewById(R.id.webview)

    val state = "testing"
    val url = "https://www.reddit.com/api/v1/authorize.compact" +
        "?client_id=${BuildConfig.REDDIT_CLIENT_ID}" +
        "&response_type=code" +
        "&state=$state" +
        "&duration=permanent" +
        "&redirect_uri=${BuildConfig.REDDIT_REDIRECT_URI}" +
        "&scope=identity,edit,flair,history,modconfig,modflair,modlog,modposts,modwiki," +
        "mysubreddits,privatemessages,read,report,save,submit,subscribe,vote,wikiedit,wikiread"

    webView.webViewClient = LogInClient()
    webView.loadUrl(url)
  }

  inner class LogInClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
      if (request?.url?.scheme == "breadit") {
        val activity = this@LogInWebViewFragment.context as LogInActivity
        activity.startLogIn(request.url.getQueryParameter("code")!!)
        return true
      }
      return false
    }
  }
}
