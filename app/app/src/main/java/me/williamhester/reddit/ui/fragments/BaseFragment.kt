package me.williamhester.reddit.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import org.greenrobot.eventbus.EventBus

import javax.inject.Inject

import butterknife.ButterKnife
import butterknife.Unbinder
import me.williamhester.reddit.BreaditApplication
import me.williamhester.reddit.apis.RedditClient
import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.ui.ContentClickCallbacks

/** A base fragment implementation containing most setup-related things. */
abstract class BaseFragment : Fragment() {

  @Inject protected lateinit var redditClient: RedditClient
    protected set
  @Inject protected lateinit var htmlParser: HtmlParser
    protected set
  @Inject protected lateinit var contentClickCallbacks: ContentClickCallbacks
    protected set
  @Inject internal lateinit var eventBus: EventBus

  private var unbinder: Unbinder? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = activity.applicationContext as BreaditApplication
    app.applicationComponent.inject(this)
  }

  override fun onStart() {
    super.onStart()

    eventBus.register(this)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    unbinder = ButterKnife.bind(this, view!!)
  }

  override fun onStop() {
    super.onStop()

    eventBus.unregister(this)
  }

  override fun onDestroyView() {
    super.onDestroyView()

    unbinder!!.unbind()
    unbinder = null
  }
}
