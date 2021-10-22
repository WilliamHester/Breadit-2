package me.williamhester.reddit.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import me.williamhester.reddit.R
import me.williamhester.reddit.messages.FailedRedditRequestMessage
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.ui.fragments.CommentsFragment
import org.greenrobot.eventbus.Subscribe

/** Activity that holds basic content.  */
open class ContentActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val f = supportFragmentManager.findFragmentById(R.id.fragment_container)
    if (f == null) {
      supportFragmentManager.beginTransaction()
          .add(R.id.fragment_container, createContentFragment()!!)
          .commit()
    }
  }

  override val layoutId: Int
    get() = R.layout.activity_content

  protected open fun createContentFragment(): Fragment? {
    val args = intent.extras
    val type = args?.getString(TYPE_EXTRA) ?: return null
    return when (type) {
      COMMENTS -> {
        val permalink = args.getString(PERMALINK_EXTRA)!!
        val s = args.getParcelable<Submission>(VOTABLE_EXTRA)!!
        CommentsFragment.newInstance(permalink, s)
      }
      else -> null
    }
  }

  @Subscribe
  fun onRequestFailure(message: FailedRedditRequestMessage) {
    Snackbar
        .make(
            findViewById(R.id.fragment_container),
            R.string.request_failed,
            BaseTransientBottomBar.LENGTH_SHORT)
        .show()
  }

  companion object {
    const val TYPE_EXTRA = "type"
    const val VOTABLE_EXTRA = "votable"
    const val PERMALINK_EXTRA = "permalink"
    const val COMMENTS = "comments"
  }
}
