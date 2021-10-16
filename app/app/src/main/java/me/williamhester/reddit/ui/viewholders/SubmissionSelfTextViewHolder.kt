package me.williamhester.reddit.ui.viewholders

import android.view.View
import android.widget.TextView

import me.williamhester.reddit.R
import me.williamhester.reddit.html.HtmlParseResult
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.ui.VotableCallbacks
import me.williamhester.reddit.ui.text.VotableMovementMethod

import butterknife.ButterKnife.findById

/** A ViewHolder for a [Submission] that has self text.  */
class SubmissionSelfTextViewHolder(
    itemView: View,
    clickListener: VotableCallbacks,
    private val htmlParser: HtmlParser
) : SubmissionViewHolder(itemView, clickListener) {

  private val selfText: TextView = findById<TextView>(itemView, R.id.self_text)
  private val linkMovementMethod: VotableMovementMethod = VotableMovementMethod(clickListener)

  init {
    this.selfText.movementMethod = linkMovementMethod
  }

  override fun setContent(item: Submission) {
    super.setContent(item)

    linkMovementMethod.setVotable(item)
    val result = htmlParser.parseHtml(item.selftextHtml)
    selfText.text = result.text
  }
}
