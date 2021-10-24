package me.williamhester.reddit.ui.viewholders

import android.view.View
import android.widget.TextView
import me.williamhester.reddit.R
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.ui.VotableCallbacks

/** A ViewHolder for a Submission object */
open class SubmissionViewHolder(
    itemView: View,
    clickListener: VotableCallbacks
) : ContentViewHolder<Submission>(itemView) {

  private val pointsTextView: TextView = itemView.findViewById(R.id.points)
  private val nsfwTextView: TextView = itemView.findViewById(R.id.nsfw)
  private val titleTextView: TextView = itemView.findViewById(R.id.title)
  private val metadata1TextView: TextView = itemView.findViewById(R.id.metadata_1)
  private val metadata2TextView: TextView = itemView.findViewById(R.id.metadata_2)

  private var submission: Submission? = null

  init {
    itemView.setOnClickListener { clickListener.onVotableClicked(submission!!) }
  }

  override fun setContent(item: Submission) {
    submission = item
    val res = itemView.context.resources
    val pointsString = res.getQuantityString(R.plurals.points, item.score)
    pointsTextView.text = String.format(pointsString, item.score)

    nsfwTextView.visibility = if (item.isNsfw) View.VISIBLE else View.INVISIBLE

    titleTextView.text = item.title

    metadata1TextView.text = String.format(res.getString(R.string.metadata_1_line),
        item.author, item.subreddit, item.domain)

    val metadata2String = res.getQuantityString(R.plurals.metadata_2_line,
        item.numComments)
    metadata2TextView.text = String.format(metadata2String, item.numComments,
        calculateShortTime(item.createdUtc))
  }

  override fun getContent(): Submission? {
    return submission
  }
}
