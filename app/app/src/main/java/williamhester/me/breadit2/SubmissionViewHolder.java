package williamhester.me.breadit2;

import android.view.View;
import android.widget.TextView;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionViewHolder extends ContentViewHolder<Submission> {
  private final TextView mPointsTextView;
  private final TextView mNsfwTextView;
  private final TextView mTitleTextView;
  private final TextView mMetadata1TextView;
  private final TextView mMetadata2TextView;

  public SubmissionViewHolder(View itemView) {
    super(itemView);

    mPointsTextView = findById(itemView, R.id.points);
    mNsfwTextView = findById(itemView, R.id.nsfw);
    mTitleTextView = findById(itemView, R.id.title);
    mMetadata1TextView = findById(itemView, R.id.metadata_1);
    mMetadata2TextView = findById(itemView, R.id.metadata_2);
  }

  @Override
  public void setContent(Submission item) {

  }
}
