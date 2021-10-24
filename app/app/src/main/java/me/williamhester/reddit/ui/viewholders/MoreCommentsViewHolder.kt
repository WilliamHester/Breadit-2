package me.williamhester.reddit.ui.viewholders

import android.view.View

import me.williamhester.reddit.R
import me.williamhester.reddit.models.MoreComment
import me.williamhester.reddit.ui.VotableCallbacks

/** ViewHolder that holds a [MoreComment].  */
class MoreCommentsViewHolder(
    itemView: View,
    clickListener: VotableCallbacks
) : CommentViewHolder<MoreComment>(itemView, clickListener) {

  override var levelIndicator: View? = null

  init {
    levelIndicator = itemView.findViewById(R.id.level_indicator)
  }
}
