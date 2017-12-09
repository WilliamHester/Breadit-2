package me.williamhester.reddit.ui.activities

import android.app.Activity
import android.os.Bundle
import io.realm.Realm
import me.williamhester.reddit.R
import me.williamhester.reddit.messages.LogInFinishedMessage
import me.williamhester.reddit.models.AccessTokenJson
import me.williamhester.reddit.models.Account
import me.williamhester.reddit.ui.fragments.LogInProgressFragment
import me.williamhester.reddit.ui.fragments.LogInWebViewFragment
import org.greenrobot.eventbus.Subscribe

/** Activity responsible for handing user log in and returning them to the app, logged in. */
class LogInActivity : BaseActivity() {

  override val layoutId: Int
    get() = R.layout.activity_content

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val fragment = fragmentManager.findFragmentById(R.id.fragment_container)
    if (fragment == null) {
      supportFragmentManager.beginTransaction()
          .add(R.id.fragment_container, LogInWebViewFragment.newInstance())
          .commit()
    }
  }

  fun startLogIn(code: String) {
    // Start blocking UI
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, LogInProgressFragment.newInstance())
        .commit()
    // Start request for token
    redditClient.logIn(code)
  }

  @Subscribe
  fun onLoggedIn(accessToken: AccessTokenJson) {
    val account = Account()
    account.accessToken = accessToken.accessToken
    account.refreshToken = accessToken.refreshToken
    // Get the account info
    redditClient.getMe(account)
  }

  @Subscribe
  fun onAccountDetailsReceived(logInFinished: LogInFinishedMessage) {
    // Set the active account
    accountManager.setAccount(logInFinished.account)
    // Save the account
    Realm.getDefaultInstance().executeTransaction {
      it.copyToRealmOrUpdate(logInFinished.account)
    }

    setResult(Activity.RESULT_OK)
    finish()
  }
}
