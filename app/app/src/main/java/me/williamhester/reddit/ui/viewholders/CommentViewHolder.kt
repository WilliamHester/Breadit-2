package me.williamhester.reddit.ui.viewholders

import android.support.v4.content.ContextCompat
import android.view.View

import me.williamhester.reddit.R
import me.williamhester.reddit.models.Comment
import me.williamhester.reddit.ui.VotableCallbacks

import java.lang.Math.max

/** ViewHolder class for holding comments; manages comments indentation and level indicators.  */
abstract class CommentViewHolder<T : Comment>(
    itemView: View,
    clickListener: VotableCallbacks
) : ContentViewHolder<T>(itemView) {

  private var comment: T? = null

  init {
    itemView.setOnClickListener { clickListener.onVotableClicked(comment!!) }
  }

  override fun setContent(item: T) {
    comment = item
    val res = itemView.resources

    val level = item.level

    val levelIndicator = levelIndicator
    if (levelIndicator != null) {
      if (level > 0) {
        levelIndicator.visibility = View.VISIBLE
        levelIndicator.setBackgroundColor(
            getBackgroundColorFromLevel(level))
      } else {
        levelIndicator.visibility = View.GONE
      }
    }

    val left = (max(level - 1, 0) * res.getDimension(R.dimen.comment_indent)).toInt()

    val top = itemView.paddingTop
    val right = itemView.paddingRight
    val bottom = itemView.paddingBottom
    itemView.setPadding(left, top, right, bottom)
  }

  override fun getContent(): T? {
    return comment
  }

  private fun getBackgroundColorFromLevel(level: Int): Int {
    val colorId = BACKGROUND_COLORS[level % BACKGROUND_COLORS.size]
    return ContextCompat.getColor(itemView.context, colorId)
  }

  /** Gets the level indicator [View] to indicate when the comment is at least 1 deep.  */
  internal abstract val levelIndicator: View?

  companion object {
    private val BACKGROUND_COLORS =
        intArrayOf(
            R.color.level_1,
            R.color.level_2,
            R.color.level_3,
            R.color.level_4)
  }
}
