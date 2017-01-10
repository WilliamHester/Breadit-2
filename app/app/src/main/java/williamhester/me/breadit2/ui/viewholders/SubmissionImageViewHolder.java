package williamhester.me.breadit2.ui.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.ui.ContentClickCallbacks;
import williamhester.me.breadit2.ui.VotableCallbacks;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionImageViewHolder extends SubmissionViewHolder {
  private final ImageView imageView;

  public SubmissionImageViewHolder(
      View itemView, final ContentClickCallbacks contentCallbacks, VotableCallbacks clickListener) {
    super(itemView, clickListener);

    imageView = ButterKnife.findById(itemView, R.id.image);
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Submission content = getContent();
        if (content == null) {
          return;
        }
        contentCallbacks.onLinkClicked(new Link(content.getUrl()));
      }
    });
  }

  @Override
  public void setContent(Submission item) {
    super.setContent(item);

    Glide.with(itemView.getContext())
        .load(item.getPreviewUrl())
        .centerCrop()
        .crossFade()
        .error(android.R.drawable.stat_notify_error)
        .into(imageView);
  }
}
