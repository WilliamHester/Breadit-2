package williamhester.me.breadit2;

import android.view.View;
import android.widget.TextView;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionLinkViewHolder extends SubmissionViewHolder {
  private final TextView mLink;

  public SubmissionLinkViewHolder(View itemView) {
    super(itemView);

    mLink = findById(itemView, R.id.link_text);
  }
}
