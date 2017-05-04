package me.williamhester.reddit.ui.viewholders;

import android.view.View;

import butterknife.ButterKnife;
import me.williamhester.reddit.R;
import me.williamhester.reddit.models.MoreComment;
import me.williamhester.reddit.ui.VotableCallbacks;

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
