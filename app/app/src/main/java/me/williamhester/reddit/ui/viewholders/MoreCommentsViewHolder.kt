package me.williamhester.reddit.ui.viewholders

import android.view.View

import butterknife.ButterKnife
import me.williamhester.reddit.R
import me.williamhester.reddit.models.MoreComment
import me.williamhester.reddit.ui.VotableCallbacks

import butterknife.ButterKnife.findById

/** ViewHolder that holds a [MoreComment].  */
class MoreCommentsViewHolder(
    itemView: View,
    clickListener: VotableCallbacks
) : CommentViewHolder<MoreComment>(itemView, clickListener) {

  override var levelIndicator: View? = null

  init {
    levelIndicator = findById<View>(itemView, R.id.level_indicator)
  }
}
