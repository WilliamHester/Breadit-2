package williamhester.me.breadit2.ui.viewholders;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.ui.HtmlParser;
import williamhester.me.breadit2.ui.VotableClickListener;
import williamhester.me.breadit2.ui.text.ClickableLinkMovementMethod;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/16/16.
 */
public class SubmissionSelfTextViewHolder extends SubmissionViewHolder {

  private TextView selfText;

  public SubmissionSelfTextViewHolder(View itemView, VotableClickListener clickListener) {
    super(itemView, clickListener);

    selfText = findById(itemView, R.id.self_text);
    selfText.setMovementMethod(new ClickableLinkMovementMethod(clickListener));
  }

  @Override
  public void setContent(Submission item) {
    super.setContent(item);

    HtmlParser parser = new HtmlParser(Html.fromHtml(item.getSelftextHtml()).toString());
    selfText.setText(parser.getSpannableString());
  }
}
