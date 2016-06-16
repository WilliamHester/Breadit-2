package williamhester.me.breadit2.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/16/16.
 */
public class SubmissionSelfTextViewHolder extends SubmissionViewHolder {

  private TextView selfText;

  public SubmissionSelfTextViewHolder(View itemView) {
    super(itemView);

    selfText = findById(itemView, R.id.self_text);
  }

  @Override
  public void setContent(Submission item) {
    super.setContent(item);


  }
}
