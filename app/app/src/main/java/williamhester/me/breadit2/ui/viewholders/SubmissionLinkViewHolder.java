package williamhester.me.breadit2.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionLinkViewHolder extends SubmissionViewHolder {
  private final TextView mLink;

  public SubmissionLinkViewHolder(View itemView) {
    super(itemView);

    mLink = ButterKnife.findById(itemView, R.id.link_text);
  }

  @Override
  public void setContent(Submission item) {
    super.setContent(item);

    mLink.setText(item.getDomain());
  }
}
