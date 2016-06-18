package williamhester.me.breadit2.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.models.TextComment;
import williamhester.me.breadit2.ui.VotableClickListener;
import williamhester.me.breadit2.ui.viewholders.ContentViewHolder;
import williamhester.me.breadit2.ui.viewholders.SubmissionImageViewHolder;
import williamhester.me.breadit2.ui.viewholders.SubmissionLinkViewHolder;
import williamhester.me.breadit2.ui.viewholders.SubmissionViewHolder;
import williamhester.me.breadit2.ui.viewholders.TextCommentViewHolder;

/**
 * Created by william on 6/13/16.
 */
public abstract class ContentAdapter extends RecyclerView.Adapter<ContentViewHolder<?>> {

  private static final int SUBMISSION = 1;
  private static final int SUBMISSION_IMAGE = 2;
  private static final int SUBMISSION_LINK = 3;
  private static final int TEXT_COMMENT = 10;

  protected LayoutInflater layoutInflater;
  protected WeakReference<VotableClickListener> clickListener;

  public ContentAdapter(LayoutInflater inflater, VotableClickListener clickListener) {
    layoutInflater = inflater;
    this.clickListener = new WeakReference<>(clickListener);
  }

  @Override
  public ContentViewHolder<?> onCreateViewHolder(ViewGroup parent, int viewType) {
    View v;
    switch (viewType) {
      case SUBMISSION_LINK:
        v = layoutInflater.inflate(R.layout.row_submission_link, parent, false);
        return new SubmissionLinkViewHolder(v, clickListener.get());
      case SUBMISSION_IMAGE:
        v = layoutInflater.inflate(R.layout.row_submission_image, parent, false);
        return new SubmissionImageViewHolder(v, clickListener.get());
      case SUBMISSION:
        v = layoutInflater.inflate(R.layout.row_submission, parent, false);
        return new SubmissionViewHolder(v, clickListener.get());
      case TEXT_COMMENT:
        v = layoutInflater.inflate(R.layout.row_comment, parent, false);
        return new TextCommentViewHolder(v, clickListener.get());
      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(ContentViewHolder<?> holder, int position) {
    switch (getItemViewType(position)) {
      case SUBMISSION_IMAGE:
      case SUBMISSION_LINK:
      case SUBMISSION:
        SubmissionViewHolder subViewHolder = (SubmissionViewHolder) holder;
        subViewHolder.itemView.setTag(position);
        subViewHolder.setContent((Submission) getItemForPosition(position));
        break;
      case TEXT_COMMENT:
        TextCommentViewHolder textCommentViewHolder = (TextCommentViewHolder) holder;
        textCommentViewHolder.itemView.setTag(position);
        textCommentViewHolder.setContent((TextComment) getItemForPosition(position));
      default:
        break;
    }
  }

  @Override
  public abstract int getItemCount();

  @Override
  public int getItemViewType(int position) {
    Object o = getItemForPosition(position);
    if (o instanceof Submission) {
      Submission sub = (Submission) o;
      if (sub.getUrl().endsWith(".png")) {
        return SUBMISSION_IMAGE;
      }
      if (!sub.isSelf()) {
        return SUBMISSION_LINK;
      }
      return SUBMISSION;
    }
    if (o instanceof TextComment) {
      return TEXT_COMMENT;
    }
    return -1;
  }

  public abstract Object getItemForPosition(int position);
}
