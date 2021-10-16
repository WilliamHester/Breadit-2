package me.williamhester.reddit.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.lang.ref.WeakReference

import me.williamhester.reddit.R
import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.models.TextComment
import me.williamhester.reddit.ui.ContentClickCallbacks
import me.williamhester.reddit.ui.VotableCallbacks
import me.williamhester.reddit.ui.viewholders.ContentViewHolder
import me.williamhester.reddit.ui.viewholders.SubmissionImageViewHolder
import me.williamhester.reddit.ui.viewholders.SubmissionLinkViewHolder
import me.williamhester.reddit.ui.viewholders.SubmissionViewHolder
import me.williamhester.reddit.ui.viewholders.TextCommentViewHolder

/** A dynamic adapter that displays the appropriate Reddit content.  */
abstract class ContentAdapter internal constructor(
    internal var layoutInflater: LayoutInflater,
    val htmlParser: HtmlParser,
    contentClickCallbacks: ContentClickCallbacks,
    clickListener: VotableCallbacks
) : RecyclerView.Adapter<ContentViewHolder<*>>() {

  protected val contentCallbacks: WeakReference<ContentClickCallbacks> =
      WeakReference(contentClickCallbacks)
  protected val clickListener: WeakReference<VotableCallbacks> =
      WeakReference(clickListener)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder<*>? {
    val v: View
    when (viewType) {
      SUBMISSION_LINK -> {
        v = layoutInflater.inflate(R.layout.row_submission_link, parent, false)
        return SubmissionLinkViewHolder(v, clickListener.get()!!)
      }
      SUBMISSION_IMAGE -> {
        v = layoutInflater.inflate(R.layout.row_submission_image, parent, false)
        return SubmissionImageViewHolder(v, contentCallbacks.get()!!, clickListener.get()!!)
      }
      SUBMISSION -> {
        v = layoutInflater.inflate(R.layout.row_submission, parent, false)
        return SubmissionViewHolder(v, clickListener.get()!!)
      }
      TEXT_COMMENT -> {
        v = layoutInflater.inflate(R.layout.row_comment, parent, false)
        return TextCommentViewHolder(v, htmlParser, clickListener.get()!!)
      }
      else -> return null
    }
  }

  override fun onBindViewHolder(holder: ContentViewHolder<*>, position: Int) {
    when (getItemViewType(position)) {
      SUBMISSION_IMAGE, SUBMISSION_LINK, SUBMISSION -> {
        val subViewHolder = holder as SubmissionViewHolder
        subViewHolder.itemView.tag = position
        subViewHolder.setContent(getItemForPosition(position) as Submission)
      }
      TEXT_COMMENT -> {
        val textCommentViewHolder = holder as TextCommentViewHolder
        textCommentViewHolder.itemView.tag = position
        textCommentViewHolder.setContent(getItemForPosition(position) as TextComment)
      }
    }
  }

  override fun getItemViewType(position: Int): Int {
    val o = getItemForPosition(position)
    if (o is Submission) {
      val sub = o
      if (sub.previewUrl != null) {
        return SUBMISSION_IMAGE
      }
      if (!sub.isSelf) {
        return SUBMISSION_LINK
      }
      return SUBMISSION
    }
    if (o is TextComment) {
      return TEXT_COMMENT
    }
    return -1
  }

  abstract fun getItemForPosition(position: Int): Any

  companion object {
    private val SUBMISSION = 1
    private val SUBMISSION_IMAGE = 2
    private val SUBMISSION_LINK = 3
    private val TEXT_COMMENT = 10
  }
}
