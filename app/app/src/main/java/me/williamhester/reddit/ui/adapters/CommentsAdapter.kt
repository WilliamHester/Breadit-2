package me.williamhester.reddit.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import me.williamhester.reddit.R
import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.models.Comment
import me.williamhester.reddit.models.MoreComment
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.ui.ContentClickCallbacks
import me.williamhester.reddit.ui.VotableCallbacks
import me.williamhester.reddit.ui.viewholders.ContentViewHolder
import me.williamhester.reddit.ui.viewholders.MoreCommentsViewHolder
import me.williamhester.reddit.ui.viewholders.SubmissionSelfTextViewHolder
import me.williamhester.reddit.ui.viewholders.SubmissionViewHolder

/**
 * An adapter for pages that are showing a comments screen. Keeps the submission at the top and
 * displays the rest of the comments in the regular tree structure.
 */
class CommentsAdapter(
    inflater: LayoutInflater,
    htmlParser: HtmlParser,
    contentClickCallbacks: ContentClickCallbacks,
    clickListener: VotableCallbacks,
    private var submission: Submission?,
    private val comments: List<Comment>
) : ContentAdapter(inflater, htmlParser, contentClickCallbacks, clickListener) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder<*> {
    return when (viewType) {
      MORE_COMMENTS -> {
        val v = layoutInflater.inflate(R.layout.row_more_comment, parent, false)
        MoreCommentsViewHolder(v, clickListener.get()!!)
      }
      SUBMISSION_SELF -> {
        val v = layoutInflater.inflate(R.layout.row_submission_self, parent, false)
        SubmissionSelfTextViewHolder(v, clickListener.get()!!, htmlParser)
      }
      else -> super.onCreateViewHolder(parent, viewType)
    }
  }

  override fun onBindViewHolder(holder: ContentViewHolder<*>, position: Int) {
    when (getItemViewType(position)) {
      MORE_COMMENTS -> {
        val moreCommentsViewHolder = holder as MoreCommentsViewHolder
        moreCommentsViewHolder.itemView.tag = position
        moreCommentsViewHolder.setContent(getItemForPosition(position) as MoreComment)
        return
      }
      SUBMISSION_SELF -> {
        val submissionViewHolder = holder as SubmissionViewHolder
        submissionViewHolder.itemView.tag = position
        submissionViewHolder.setContent(submission!!)
        return
      }
      else -> super.onBindViewHolder(holder, position)
    }
  }

  override fun getItemViewType(position: Int): Int {
    if (position == 0 && submission != null) {
      return if (!submission!!.selftextHtml.isEmpty()) {
        SUBMISSION_SELF
      } else {
        super.getItemViewType(position)
      }
    }
    if (getItemForPosition(position) is MoreComment) {
      return MORE_COMMENTS
    }
    return super.getItemViewType(position)
  }

  override fun getItemCount(): Int {
    return (if (submission != null) 1 else 0) + comments.size
  }

  override fun getItemForPosition(position: Int): Any {
    if (position == 0 && submission != null) {
      return submission as Submission
    }
    return comments[position - (if (submission == null) 0 else 1)]
  }

  companion object {
    private const val SUBMISSION_SELF = 4
    private const val MORE_COMMENTS = 11
  }
}
