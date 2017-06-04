package me.williamhester.reddit.ui.activities

import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.widget.FrameLayout

import org.greenrobot.eventbus.Subscribe

import me.williamhester.reddit.R
import me.williamhester.reddit.messages.FailedRedditRequestMessage
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.ui.fragments.CommentsFragment

/** Activity that holds basic content.  */
open class ContentActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val f = supportFragmentManager.findFragmentById(R.id.fragment_container)
    if (f == null) {
      supportFragmentManager.beginTransaction()
          .add(R.id.fragment_container, createContentFragment())
          .commit()
    }
  }

  override val layoutId: Int
    get() = R.layout.activity_content

  protected open fun createContentFragment(): Fragment? {
    val args = intent.extras
    val type = args.getString(TYPE_EXTRA) ?: return null
    when (type) {
      COMMENTS -> {
        val permalink = args.getString(PERMALINK_EXTRA)
        val s = args.getParcelable<Submission>(VOTABLE_EXTRA)
        return CommentsFragment.newInstance(permalink, s)
      }
      else -> return null
    }
  }

  @Subscribe
  fun onRequestFailure(message: FailedRedditRequestMessage) {
    Snackbar
        .make(
            findViewById(R.id.fragment_container) as FrameLayout,
            R.string.request_failed,
            BaseTransientBottomBar.LENGTH_SHORT)
        .show()
  }

  companion object {

    val TYPE_EXTRA = "type"
    val VOTABLE_EXTRA = "votable"
    val PERMALINK_EXTRA = "permalink"

    val COMMENTS = "comments"
  }
}
