package me.williamhester.reddit.ui.viewholders

import android.view.View
import android.widget.TextView

import me.williamhester.reddit.R
import me.williamhester.reddit.models.TextComment
import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.ui.VotableCallbacks
import me.williamhester.reddit.ui.text.VotableMovementMethod

/**
 * Holds a comment that contains text and perform
 */
class TextCommentViewHolder(
    itemView: View,
    private val htmlParser: HtmlParser,
    clickListener: VotableCallbacks
) : CommentViewHolder<TextComment>(itemView, clickListener) {

  private val author: TextView = itemView.findViewById(R.id.author)
  private val flair: TextView = itemView.findViewById(R.id.flair)
  private val metadata: TextView = itemView.findViewById(R.id.metadata)
  private val body: TextView = itemView.findViewById(R.id.body)
  override val levelIndicator: View = itemView.findViewById(R.id.level_indicator)
  private val linkMovementMethod = VotableMovementMethod(clickListener)

  init {
    body.movementMethod = linkMovementMethod
  }

  override fun setContent(item: TextComment) {
    super.setContent(item)
    linkMovementMethod.setVotable(item)

    val res = itemView.resources

    author.text = item.author
    flair.text = item.authorFlairText
    metadata.text = String.format(res.getString(R.string.comment_metadata),
        item.score,
        calculateShortTime(item.createdUtc))

    if (item.isHidden) {
      body.visibility = View.GONE
    } else {
      body.visibility = View.VISIBLE
      val result = htmlParser.parseHtml(item.bodyHtml)
      body.text = result.text
    }
  }
}
