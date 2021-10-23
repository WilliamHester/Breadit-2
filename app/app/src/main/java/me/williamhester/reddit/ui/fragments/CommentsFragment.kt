package me.williamhester.reddit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import me.williamhester.reddit.messages.PostMessage
import me.williamhester.reddit.models.*
import me.williamhester.reddit.ui.adapters.CommentsAdapter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/** A fragment containing Comments. */
class CommentsFragment : ContentFragment<CommentsAdapter>() {

  private var permalink: String? = null
  private var submission: Submission? = null
  private val comments = ArrayList<Comment>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    permalink = requireArguments().getString(PERMALINK)
    submission = requireArguments().getParcelable(SUBMISSION)

    loadContent()
  }

  override fun loadContent() {
    redditClient.getComments(permalink!!)
  }

  override fun createAdapter(savedInstanceState: Bundle?): CommentsAdapter {
    return CommentsAdapter(
        LayoutInflater.from(activity),
        htmlParser,
        contentClickCallbacks,
        this,
        submission,
        comments)
  }

  override fun onVotableClicked(votable: Votable) {
    if (votable is TextComment) {
      val position = comments.indexOf(votable) + 1
      val comment = votable
      if (comment.isHidden) {
        expandComment(position, comment)
      } else {
        collapseComment(position, comment)
      }
      return
    }
    if (votable is MoreComment) {
      // TODO: Load more comments
      return
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onCommentsLoaded(message: PostMessage) {
    isLoading = false
    val post = message.post
    val oldAdapterCount = adapter.itemCount
    submission = post.submission
    comments.clear()
    comments.addAll(post.comments)

    adapter.notifyItemRangeInserted(oldAdapterCount, comments.size + 1 - oldAdapterCount)
    if (oldAdapterCount > 0) {
      adapter.notifyItemRangeChanged(0, oldAdapterCount)
    }
  }

  private fun collapseComment(position: Int, comment: TextComment) {
    val collapsedComments = ArrayList<Comment>()
    val start = position
    var c = comments[position]
    var endPosition = position

    while (c.level > comment.level) {
      endPosition++
      if (endPosition < comments.size) {
        c = comments[endPosition]
      } else {
        break
      }
    }

    val subList = comments.subList(start, endPosition)
    collapsedComments.addAll(subList)
    subList.clear()
    comment.children = collapsedComments
    adapter.notifyItemChanged(start)
    adapter.notifyItemRangeRemoved(start + 1, endPosition - start)
    recyclerView.scrollToPosition(start)
  }

  private fun expandComment(position: Int, comment: TextComment) {
    val commentsSubList = comments.subList(position - 1, position)
    commentsSubList.addAll(comment.children)
    val itemCount = comment.children.size
    comment.children = null

    adapter.notifyItemChanged(position)
    adapter.notifyItemRangeInserted(position + 1, itemCount)
  }

  companion object {
    private val PERMALINK = "permalink"
    private val SUBMISSION = "submission"

    fun newInstance(permalink: String, s: Submission): CommentsFragment {
      val args = Bundle()
      args.putString(PERMALINK, permalink)
      args.putParcelable(SUBMISSION, s)
      val fragment = CommentsFragment()
      fragment.arguments = args
      return fragment
    }
  }
}
