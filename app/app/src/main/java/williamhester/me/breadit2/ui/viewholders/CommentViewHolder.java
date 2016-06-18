package williamhester.me.breadit2.ui.viewholders;

import android.content.res.Resources;
import android.view.View;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Comment;
import williamhester.me.breadit2.ui.VotableClickListener;

/**
 * Created by william on 6/16/16.
 */
public class CommentViewHolder<T extends Comment> extends ContentViewHolder<T> {

  private Comment comment;

  public CommentViewHolder(View itemView, final VotableClickListener clickListener) {
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

    int left = (int) ((1 + item.getLevel()) * res.getDimension(R.dimen.comment_indent));

    int top = itemView.getPaddingTop();
    int right = itemView.getPaddingRight();
    int bottom = itemView.getPaddingBottom();
    itemView.setPadding(left, top, right, bottom);
  }
}
