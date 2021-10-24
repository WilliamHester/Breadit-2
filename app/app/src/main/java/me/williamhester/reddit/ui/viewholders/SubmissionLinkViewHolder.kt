package me.williamhester.reddit.ui.viewholders

import android.view.View
import android.widget.TextView

import me.williamhester.reddit.R
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.ui.VotableCallbacks

/** A ViewHolder for a Submission object. */
class SubmissionLinkViewHolder(
    itemView: View,
    clickListener: VotableCallbacks
) : SubmissionViewHolder(itemView, clickListener) {

  private val linkText: TextView = itemView.findViewById(R.id.link_text)

  init {
    linkText.setOnClickListener {
      // TODO: click submission link
      //        clickListener.onLinkClicked();
    }
  }

  override fun setContent(item: Submission) {
    super.setContent(item)

    linkText.text = item.domain
  }
}
