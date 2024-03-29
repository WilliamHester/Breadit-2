package me.williamhester.reddit.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import me.williamhester.reddit.apis.RedditClient
import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.ui.ContentClickCallbacks
import me.williamhester.reddit.ui.activities.INSTANCE_FRAGMENT_TAG
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/** A base fragment implementation containing most setup-related things. */
abstract class BaseFragment : Fragment() {

  @Inject protected lateinit var redditClient: RedditClient
  @Inject protected lateinit var htmlParser: HtmlParser
  @Inject protected lateinit var contentClickCallbacks: ContentClickCallbacks
  @Inject protected lateinit var eventBus: EventBus

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val instanceFragment =
        parentFragmentManager.findFragmentByTag(INSTANCE_FRAGMENT_TAG) as InstanceFragment
    instanceFragment.activityComponent.inject(this)
  }

  override fun onStart() {
    super.onStart()

    eventBus.register(this)
  }

  override fun onStop() {
    super.onStop()

    eventBus.unregister(this)
  }

  @Subscribe
  fun noOp(nothing: Nothing) {
    // Simply subscribe to "Nothing" so that if a subclass doesn't subscribe to anything, the
    // EventBus won't crash.
  }
}
