package me.williamhester.reddit.ui.viewholders;

import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

import me.williamhester.reddit.R;
import me.williamhester.reddit.html.HtmlParser;
import me.williamhester.reddit.models.Comment;
import me.williamhester.reddit.ui.VotableCallbacks;

import static java.lang.Math.max;

/** ViewHolder class for holding comments; manages comments indentation and level indicators. */
abstract class CommentViewHolder<T extends Comment> extends ContentViewHolder<T> {

  private static final int[] BACKGROUND_COLORS = {
      R.color.level_1,
      R.color.level_2,
      R.color.level_3,
      R.color.level_4
  };

  private Comment comment;

  CommentViewHolder(View itemView, final VotableCallbacks clickListener) {
    super(itemView);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickListener.onVotableClicked(comment);
      }
    });
  }

  @Override
  public void setContent(T item) {
    comment = item;
    Resources res = itemView.getResources();

    int level = item.getLevel();

    View levelIndicator = getLevelIndicator();
    if (levelIndicator != null) {
      if (level > 0) {
        levelIndicator.setVisibility(View.VISIBLE);
        levelIndicator.setBackgroundColor(
            getBackgroundColorFromLevel(level));
      } else {
        levelIndicator.setVisibility(View.GONE);
      }
    }

    int left = (int) (max(level - 1, 0) * res.getDimension(R.dimen.comment_indent));

    int top = itemView.getPaddingTop();
    int right = itemView.getPaddingRight();
    int bottom = itemView.getPaddingBottom();
    itemView.setPadding(left, top, right, bottom);
  }

  private int getBackgroundColorFromLevel(int level) {
    int colorId = BACKGROUND_COLORS[level % BACKGROUND_COLORS.length];
    return ContextCompat.getColor(itemView.getContext(), colorId);
  }

  /** Gets the level indicator {@link View} to indicate when the comment is at least 1 deep. */
  abstract View getLevelIndicator();
}
