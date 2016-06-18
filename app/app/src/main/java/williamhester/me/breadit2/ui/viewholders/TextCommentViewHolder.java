package williamhester.me.breadit2.ui.viewholders;

import android.content.res.Resources;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.TextComment;
import williamhester.me.breadit2.ui.HtmlParser;
import williamhester.me.breadit2.ui.VotableClickListener;

import static butterknife.ButterKnife.findById;

/**
 * Created by william on 6/16/16.
 */
public class TextCommentViewHolder extends CommentViewHolder<TextComment> {

  private TextView author;
  private TextView flair;
  private TextView metadata;
  private TextView body;

  public TextCommentViewHolder(View itemView, final VotableClickListener clickListener) {
    super(itemView, clickListener);

    author = findById(itemView, R.id.author);
    flair = findById(itemView, R.id.flair);
    metadata = findById(itemView, R.id.metadata);
    body = findById(itemView, R.id.body);
  }

  @Override
  public void setContent(TextComment item) {
    super.setContent(item);
    Resources res = itemView.getResources();

    author.setText(item.getAuthor());
    String flairText = item.getAuthorFlairText();
    if (flairText == null) {
      flairText = "";
    }
    flair.setText(Html.fromHtml(flairText).toString());
    metadata.setText(String.format(res.getString(R.string.comment_metadata),
        item.getScore(),
        item.getCreatedUtc()));

    if (item.isHidden()) {
      body.setVisibility(View.GONE);
    } else {
      body.setVisibility(View.VISIBLE);
      HtmlParser parser = new HtmlParser(Html.fromHtml(item.getBodyHtml()).toString());
      body.setText(parser.getSpannableString());
    }

  }
}
