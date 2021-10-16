package me.williamhester.reddit.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.williamhester.reddit.BreaditApplication
import me.williamhester.reddit.apis.RedditClient
import me.williamhester.reddit.inject.ActivityModule
import me.williamhester.reddit.messages.StartActivityMessage
import me.williamhester.reddit.models.managers.AccountManager
import me.williamhester.reddit.ui.fragments.InstanceFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

val INSTANCE_FRAGMENT_TAG = "instance_fragment"

abstract class BaseActivity : AppCompatActivity() {

  @Inject lateinit protected var bus: EventBus
  @Inject lateinit protected var redditClient: RedditClient
  @Inject lateinit protected var accountManager: AccountManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutId)

    var fragment =
        supportFragmentManager.findFragmentByTag(INSTANCE_FRAGMENT_TAG) as? InstanceFragment
    if (fragment == null) {
      fragment = InstanceFragment.newInstance()
      supportFragmentManager.beginTransaction()
          .add(fragment, INSTANCE_FRAGMENT_TAG)
          .commit()

      // Create and assign the Activity component
      val app = applicationContext as BreaditApplication
      fragment.activityComponent = app.applicationComponent.plus(ActivityModule())
    }
    fragment.activityComponent.inject(this)
  }

  override fun onStart() {
    super.onStart()

    bus.register(this)
  }

  override fun onStop() {
    super.onStop()

    bus.unregister(this)
  }

  @Subscribe
  fun startActivity(request: StartActivityMessage) {
    request.startActivity(this)
  }

  protected abstract val layoutId: Int
}
