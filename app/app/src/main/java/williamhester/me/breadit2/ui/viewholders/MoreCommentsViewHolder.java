package williamhester.me.breadit2.ui.viewholders;

import android.view.View;

import butterknife.ButterKnife;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.MoreComment;
import williamhester.me.breadit2.ui.VotableCallbacks;

import static butterknife.ButterKnife.findById;

/** ViewHolder that holds a {@link MoreComment}. */
public class MoreCommentsViewHolder extends CommentViewHolder<MoreComment> {

  private final View levelIndicator;

  public MoreCommentsViewHolder(View itemView, VotableCallbacks clickListener) {
    super(itemView, clickListener);

    levelIndicator = findById(itemView, R.id.level_indicator);
  }

  @Override
  View getLevelIndicator() {
    return levelIndicator;
  }
}
