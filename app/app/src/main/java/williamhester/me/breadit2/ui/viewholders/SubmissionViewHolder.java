package williamhester.me.breadit2.ui.viewholders;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.html.HtmlParser;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.ui.VotableCallbacks;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionViewHolder extends ContentViewHolder<Submission> {

  private final TextView pointsTextView;
  private final TextView nsfwTextView;
  private final TextView titleTextView;
  private final TextView metadata1TextView;
  private final TextView metadata2TextView;

  private Submission submission;

  public SubmissionViewHolder(
      View itemView, final VotableCallbacks clickListener) {
    super(itemView);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickListener.onVotableClicked(submission);
      }
    });

    pointsTextView = ButterKnife.findById(itemView, R.id.points);
    nsfwTextView = findById(itemView, R.id.nsfw);
    titleTextView = findById(itemView, R.id.title);
    metadata1TextView = findById(itemView, R.id.metadata_1);
    metadata2TextView = findById(itemView, R.id.metadata_2);
  }

  @Override
  public void setContent(Submission item) {
    submission = item;
    Resources res = itemView.getContext().getResources();
    String pointsString = res.getQuantityString(R.plurals.points, item.getScore());
    pointsTextView.setText(String.format(pointsString, item.getScore()));

    nsfwTextView.setVisibility(item.isNsfw() ? View.VISIBLE : View.INVISIBLE);

    titleTextView.setText(item.getTitle());

    metadata1TextView.setText(String.format(res.getString(R.string.metadata_1_line),
        item.getAuthor(), item.getSubreddit(), item.getDomain()));

    String metadata2String = res.getQuantityString(R.plurals.metadata_2_line,
        item.getNumComments());
    metadata2TextView.setText(String.format(metadata2String, item.getNumComments(),
        calculateShortTime(item.getCreatedUtc())));
  }

  /** Gets the underlying content. */
  protected Submission getContent() {
    return submission;
  }
}
