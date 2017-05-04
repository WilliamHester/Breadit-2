package me.williamhester.reddit.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import me.williamhester.reddit.R;
import me.williamhester.reddit.html.HtmlParseResult;
import me.williamhester.reddit.models.Submission;
import me.williamhester.reddit.html.HtmlParser;
import me.williamhester.reddit.ui.VotableCallbacks;
import me.williamhester.reddit.ui.text.VotableMovementMethod;

import static butterknife.ButterKnife.findById;

/** A ViewHolder for a {@link Submission} that has self text. */
public class SubmissionSelfTextViewHolder extends SubmissionViewHolder {

  private final HtmlParser htmlParser;
  private final TextView selfText;
  private final VotableMovementMethod linkMovementMethod;

  public SubmissionSelfTextViewHolder(
      View itemView, HtmlParser htmlParser, VotableCallbacks clickListener) {
    super(itemView, clickListener);

    this.htmlParser = htmlParser;
    this.selfText = findById(itemView, R.id.self_text);
    this.linkMovementMethod = new VotableMovementMethod(clickListener);
    this.selfText.setMovementMethod(linkMovementMethod);
  }

  @Override
  public void setContent(Submission item) {
    super.setContent(item);

    linkMovementMethod.setVotable(item);
    HtmlParseResult result = htmlParser.parseHtml(item.getSelftextHtml());
    selfText.setText(result.getText());
  }
}
