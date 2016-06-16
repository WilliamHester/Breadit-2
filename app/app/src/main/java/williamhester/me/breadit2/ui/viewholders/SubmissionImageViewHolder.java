package williamhester.me.breadit2.ui.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionImageViewHolder extends SubmissionViewHolder {
  private final ImageView imageView;

  public SubmissionImageViewHolder(View itemView) {
    super(itemView);

    imageView = ButterKnife.findById(itemView, R.id.image);
  }

  @Override
  public void setContent(Submission item) {
    super.setContent(item);

    Glide.with(itemView.getContext())
        .load(item.getUrl())
        .centerCrop()
        .crossFade()
        .into(imageView);
  }
}
