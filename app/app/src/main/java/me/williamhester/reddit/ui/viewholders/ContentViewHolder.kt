package me.williamhester.reddit.ui.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View

/** A base class for a [RecyclerView.ViewHolder] that holds some content of type T. */
abstract class ContentViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

  abstract fun setContent(item: T)

  abstract fun getContent() : T?

  protected fun calculateShortTime(timeInSeconds: Int): String {
    val currentTime = (System.currentTimeMillis() / 1000).toInt()
    val diff = Math.max(currentTime - timeInSeconds, 0)
    if (diff < 60) {
      return diff.toString() + "s"
    }
    if (diff < 3600) {
      return (diff / 60).toString() + "m"
    }
    if (diff < 3600 * 24) {
      return (diff / 3600).toString() + "h"
    }
    if (diff < 3600 * 24 * 30) {
      return (diff / (3600 * 24)).toString() + "d"
    }
    if (diff < 3600 * 24 * 30 * 12) {
      return (diff / (3600 * 24 * 30)).toString() + "mo"
    }
    return (diff / (3600 * 24 * 30 * 12)).toString() + "y"
  }
}
