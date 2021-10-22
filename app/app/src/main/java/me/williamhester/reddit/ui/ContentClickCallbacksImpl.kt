package me.williamhester.reddit.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.browser.customtabs.CustomTabsIntent

import org.greenrobot.eventbus.EventBus

import me.williamhester.reddit.messages.StartActivityMessage
import me.williamhester.reddit.models.Link
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.ui.activities.ContentActivity

/** Callbacks called when a link is clicked.  */
class ContentClickCallbacksImpl(private val bus: EventBus) : ContentClickCallbacks {

  override fun onLinkClicked(link: Link) {
    val customTab = CustomTabsIntent.Builder().build()
    bus.post(object : StartActivityMessage() {
      override fun startActivity(context: Context) {
        customTab.launchUrl(context, link.uri)
      }
    })
  }

  override fun navigateTo(parcelable: Parcelable) {
    if (parcelable !is Submission) {
      return
    }

    val args = Bundle()
    args.putParcelable(ContentActivity.VOTABLE_EXTRA, parcelable)
    args.putString(ContentActivity.TYPE_EXTRA, ContentActivity.COMMENTS)
    args.putString(ContentActivity.PERMALINK_EXTRA, parcelable.permalink)

    bus.post(object : StartActivityMessage() {
      override fun startActivity(context: Context) {
        val i = Intent(context, ContentActivity::class.java)
        i.putExtras(args)
        context.startActivity(i)
      }
    })
  }
}
