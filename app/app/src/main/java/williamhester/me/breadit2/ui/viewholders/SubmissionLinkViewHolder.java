package williamhester.me.breadit2.ui.viewholders;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.ui.viewholders.SubmissionViewHolder;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionLinkViewHolder extends SubmissionViewHolder {
  private final TextView mLink;

  public SubmissionLinkViewHolder(View itemView) {
    super(itemView);

    mLink = ButterKnife.findById(itemView, R.id.link_text);
  }
}
