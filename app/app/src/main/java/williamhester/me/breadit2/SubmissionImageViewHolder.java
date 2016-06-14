package williamhester.me.breadit2;

import android.view.View;
import android.widget.ImageView;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionImageViewHolder extends SubmissionViewHolder {
  private final ImageView mImage;

  public SubmissionImageViewHolder(View itemView) {
    super(itemView);

    mImage = findById(itemView, R.id.image);
  }
}
