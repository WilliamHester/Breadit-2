package williamhester.me.breadit2.ui.viewholders;

import android.view.View;

import williamhester.me.breadit2.models.MoreComment;
import williamhester.me.breadit2.ui.VotableClickListener;

/**
 * Created by william on 6/16/16.
 */
public class MoreCommentsViewHolder extends CommentViewHolder<MoreComment> {
  public MoreCommentsViewHolder(View itemView, VotableClickListener clickListener) {
    super(itemView, clickListener);
  }

  @Override
  public void setContent(MoreComment item) {
    super.setContent(item);
  }
}
