package me.williamhester.reddit.ui.text

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView

import me.williamhester.reddit.models.Votable
import me.williamhester.reddit.ui.VotableCallbacks

/**
 * A [LinkMovementMethod] that propogates the click to a [VotableCallbacks] if a link is not
 * clicked.
 */
class VotableMovementMethod(private val votableCallbacks: VotableCallbacks) : LinkMovementMethod() {
  private var votable: Votable? = null

  fun setVotable(votable: Votable) {
    this.votable = votable
  }

  override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
    if (event.action == MotionEvent.ACTION_UP) {
      if (!super.onTouchEvent(widget, buffer, event)) {
        votableCallbacks.onVotableClicked(votable!!)
        return false
      } else {
        return true
      }
    }
    return super.onTouchEvent(widget, buffer, event)
  }
}
