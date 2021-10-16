package me.williamhester.reddit.ui.viewholders

import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide

import butterknife.ButterKnife
import me.williamhester.reddit.R
import me.williamhester.reddit.models.Link
import me.williamhester.reddit.models.Submission
import me.williamhester.reddit.ui.ContentClickCallbacks
import me.williamhester.reddit.ui.VotableCallbacks

/** A ViewHolder for a Submission that has an image. */
class SubmissionImageViewHolder(
    itemView: View,
    contentCallbacks: ContentClickCallbacks,
    clickListener: VotableCallbacks
) : SubmissionViewHolder(itemView, clickListener) {

  private val imageView: ImageView = ButterKnife.findById<ImageView>(itemView, R.id.image)

  init {
    imageView.setOnClickListener(View.OnClickListener {
      val content = getContent() ?: return@OnClickListener
      contentCallbacks.onLinkClicked(Link(content.url))
    })
  }

  override fun setContent(item: Submission) {
    super.setContent(item)

    Glide.with(itemView.context)
        .load(item.previewUrl)
        .centerCrop()
        .crossFade()
        .error(android.R.drawable.stat_notify_error)
        .into(imageView)
  }
}
