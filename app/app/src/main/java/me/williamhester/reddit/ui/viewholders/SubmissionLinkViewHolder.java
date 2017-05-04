package me.williamhester.reddit.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import me.williamhester.reddit.R;
import me.williamhester.reddit.models.Submission;
import me.williamhester.reddit.ui.VotableCallbacks;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionLinkViewHolder extends SubmissionViewHolder {
  private final TextView linkText;

  public SubmissionLinkViewHolder(View itemView, final VotableCallbacks clickListener) {
    super(itemView, clickListener);

    linkText = ButterKnife.findById(itemView, R.id.link_text);
    linkText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO: click submission link
//        clickListener.onLinkClicked();
      }
    });
  }

  @Override
  public void setContent(Submission item) {
    super.setContent(item);

    linkText.setText(item.getDomain());
  }
}
