package me.williamhester.reddit.ui.text

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/** A [ClickableSpan] that hides the text until it is clicked. */
class SpoilerSpan : ClickableSpan() {

  private var clicked = false

  override fun onClick(view: View) {
    val unpaint = TextPaint()
    clicked = true
    updateDrawState(unpaint)
    view.invalidate()
  }

  override fun updateDrawState(ds: TextPaint) {
    ds.isUnderlineText = true
    if (!clicked) {
      ds.bgColor = ds.color
    } else {
      ds.bgColor = 0
    }
  }
}
