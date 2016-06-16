package williamhester.me.breadit2.ui.viewholders;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;

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

    mPointsTextView = ButterKnife.findById(itemView, R.id.points);
    mNsfwTextView = findById(itemView, R.id.nsfw);
    mTitleTextView = findById(itemView, R.id.title);
    mMetadata1TextView = findById(itemView, R.id.metadata_1);
    mMetadata2TextView = findById(itemView, R.id.metadata_2);
  }

  @Override
  public void setContent(Submission item) {
    Resources res = itemView.getContext().getResources();
    String pointsString = res.getQuantityString(R.plurals.points, item.getScore());
    mPointsTextView.setText(String.format(pointsString, item.getScore()));

    mNsfwTextView.setVisibility(item.isNsfw() ? View.VISIBLE : View.INVISIBLE);

    mTitleTextView.setText(item.getTitle());

    mMetadata1TextView.setText(String.format(res.getString(R.string.metadata_1_line),
        item.getAuthor(), item.getSubreddit(), item.getDomain()));

    String metadata2String = res.getQuantityString(R.plurals.metadata_2_line,
        item.getNumComments());
    mMetadata2TextView.setText(String.format(metadata2String, item.getNumComments(),
        item.getCreatedUtc()));
  }
}
