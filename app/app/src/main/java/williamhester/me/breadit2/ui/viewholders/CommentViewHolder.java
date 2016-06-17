package williamhester.me.breadit2.ui.viewholders;

import android.content.res.Resources;
import android.view.View;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Comment;

/**
 * Created by william on 6/16/16.
 */
public class CommentViewHolder<T extends Comment> extends ContentViewHolder<T> {
  public CommentViewHolder(View itemView) {
    super(itemView);
  }

  @Override
  public void setContent(T item) {
    Resources res = itemView.getResources();

    int left = (int) ((1 + item.getLevel()) * res.getDimension(R.dimen.comment_indent));

    int top = itemView.getPaddingTop();
    int right = itemView.getPaddingRight();
    int bottom = itemView.getPaddingBottom();
    itemView.setPadding(left, top, right, bottom);
  }
}
