package me.williamhester.reddit.ui.text

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

import me.williamhester.reddit.models.Link
import me.williamhester.reddit.models.Link.*
import me.williamhester.reddit.ui.ContentClickCallbacks

/**
 * A [ClickableSpan] that shows a [Link].
 * The color is dependent upon the [Link]'s type.
 */
class LinkSpan(private val link: Link, private val callbacks: ContentClickCallbacks) : ClickableSpan() {

  override fun updateDrawState(ds: TextPaint) {
    super.updateDrawState(ds)
    when (link.type) {
      IMGUR_IMAGE,
      IMGUR_ALBUM,
      IMGUR_GALLERY,
      NORMAL_IMAGE,
      GFYCAT_LINK,
      GIF,
      DIRECT_GFY ->
        ds.color = GREEN
      YOUTUBE ->
        ds.color = RED
      MESSAGES,
      SUBMISSION,
      SUBREDDIT,
      USER,
      REDDIT_LIVE ->
        ds.color = ORANGE
    }
  }

  override fun onClick(view: View) {
    callbacks.onLinkClicked(link)
  }

  companion object {
    private val ORANGE = 0xfff68026.toInt()
    private val RED = 0xffb31217.toInt()
    private val GREEN = 0xff85c025.toInt()
  }
}

