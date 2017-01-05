package williamhester.me.breadit2.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.ui.HtmlParser;
import williamhester.me.breadit2.ui.VotableCallbacks;
import williamhester.me.breadit2.ui.text.VotableMovementMethod;

import static butterknife.ButterKnife.findById;
import static williamhester.me.breadit2.util.Util.unescapeHtml;

/** A ViewHolder for a {@link Submission} that has self text. */
public class SubmissionSelfTextViewHolder extends SubmissionViewHolder {

  private final TextView selfText;
  private final VotableMovementMethod linkMovementMethod;

  public SubmissionSelfTextViewHolder(View itemView, VotableCallbacks clickListener) {
    super(itemView, clickListener);

    selfText = findById(itemView, R.id.self_text);
    linkMovementMethod = new VotableMovementMethod(clickListener);
    selfText.setMovementMethod(linkMovementMethod);
  }

  @Override
  public void setContent(Submission item) {
    super.setContent(item);

    linkMovementMethod.setVotable(item);
    HtmlParser parser = new HtmlParser(unescapeHtml(item.getSelftextHtml()));
    selfText.setText(parser.getSpannableString());
  }
}
