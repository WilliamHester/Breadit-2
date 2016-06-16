package williamhester.me.breadit2.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import butterknife.ButterKnife;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.ui.viewholders.SubmissionViewHolder;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionImageViewHolder extends SubmissionViewHolder {
  private final ImageView mImage;

  public SubmissionImageViewHolder(View itemView) {
    super(itemView);

    mImage = ButterKnife.findById(itemView, R.id.image);
  }
}
