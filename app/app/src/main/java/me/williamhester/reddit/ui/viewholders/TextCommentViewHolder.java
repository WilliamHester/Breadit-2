package me.williamhester.reddit.ui.viewholders;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import me.williamhester.reddit.R;
import me.williamhester.reddit.html.HtmlParseResult;
import me.williamhester.reddit.models.TextComment;
import me.williamhester.reddit.html.HtmlParser;
import me.williamhester.reddit.ui.VotableCallbacks;
import me.williamhester.reddit.ui.text.VotableMovementMethod;

import static butterknife.ButterKnife.findById;

/**
 * Holds a comment that contains text and perform
 */
public class TextCommentViewHolder extends CommentViewHolder<TextComment> {

  private final HtmlParser htmlParser;
  private final TextView author;
  private final TextView flair;
  private final TextView metadata;
  private final TextView body;
  private final View levelIndicator;
  private final VotableMovementMethod linkMovementMethod;

  public TextCommentViewHolder(
      View itemView, HtmlParser htmlParser, final VotableCallbacks clickListener) {
    super(itemView, clickListener);

    this.htmlParser = htmlParser;
    author = findById(itemView, R.id.author);
    flair = findById(itemView, R.id.flair);
    metadata = findById(itemView, R.id.metadata);
    body = findById(itemView, R.id.body);
    levelIndicator = findById(itemView, R.id.level_indicator);
    linkMovementMethod = new VotableMovementMethod(clickListener);
    body.setMovementMethod(linkMovementMethod);
  }

  @Override
  public void setContent(TextComment item) {
    super.setContent(item);
    linkMovementMethod.setVotable(item);

    Resources res = itemView.getResources();

    author.setText(item.getAuthor());
    flair.setText(item.getAuthorFlairText());
    metadata.setText(String.format(res.getString(R.string.comment_metadata),
        item.getScore(),
        calculateShortTime(item.getCreatedUtc())));

    if (item.isHidden()) {
      body.setVisibility(View.GONE);
    } else {
      body.setVisibility(View.VISIBLE);
      HtmlParseResult result = htmlParser.parseHtml(item.getBodyHtml());
      body.setText(result.getText());
    }
  }

  @Override
  View getLevelIndicator() {
    return levelIndicator;
  }
}
