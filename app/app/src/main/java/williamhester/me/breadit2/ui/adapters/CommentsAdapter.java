package williamhester.me.breadit2.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Comment;
import williamhester.me.breadit2.models.MoreComment;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.ui.VotableClickListener;
import williamhester.me.breadit2.ui.viewholders.ContentViewHolder;
import williamhester.me.breadit2.ui.viewholders.MoreCommentsViewHolder;
import williamhester.me.breadit2.ui.viewholders.SubmissionSelfTextViewHolder;
import williamhester.me.breadit2.ui.viewholders.SubmissionViewHolder;

/**
 * Created by william on 6/16/16.
 */
public class CommentsAdapter extends ContentAdapter {

  private static final int SUBMISSION_SELF = 4;
  private static final int MORE_COMMENTS = 11;

  private final List<Comment> comments;
  private Submission submission;

  public CommentsAdapter(LayoutInflater inflater, VotableClickListener clickListener,
                         Submission submission, List<Comment> comments) {
    super(inflater, clickListener);

    this.submission = submission;
    this.comments = comments;
  }

  public void setSubmission(Submission submission) {
    this.submission = submission;
  }

  @Override
  public ContentViewHolder<?> onCreateViewHolder(ViewGroup parent, int viewType) {
    View v;
    switch (viewType) {
      case MORE_COMMENTS:
        v = layoutInflater.inflate(R.layout.row_more_comment, parent, false);
        return new MoreCommentsViewHolder(v, clickListener.get());
      case SUBMISSION_SELF:
        v = layoutInflater.inflate(R.layout.row_submission_self, parent, false);
        return new SubmissionSelfTextViewHolder(v, clickListener.get());
      default:
        return super.onCreateViewHolder(parent, viewType);
    }
  }

  @Override
  public void onBindViewHolder(ContentViewHolder<?> holder, int position) {
    switch (getItemViewType(position)) {
      case MORE_COMMENTS:
        MoreCommentsViewHolder moreCommentsViewHolder = (MoreCommentsViewHolder) holder;
        moreCommentsViewHolder.itemView.setTag(position);
        moreCommentsViewHolder.setContent((MoreComment) getItemForPosition(position));
        return;
      case SUBMISSION_SELF:
        SubmissionViewHolder submissionViewHolder = (SubmissionViewHolder) holder;
        submissionViewHolder.itemView.setTag(position);
        submissionViewHolder.setContent(submission);
        return;
      default:
        super.onBindViewHolder(holder, position);
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0 && submission != null) {
      if (submission.isSelf()) {
        return SUBMISSION_SELF;
      } else {
        return super.getItemViewType(position);
      }
    }
    if (getItemForPosition(position) instanceof MoreComment) {
      return MORE_COMMENTS;
    }
    return super.getItemViewType(position);
  }

  @Override
  public int getItemCount() {
    return (submission != null ? 1 : 0) + comments.size();
  }

  @Override
  public Object getItemForPosition(int position) {
    if (position == 0 && submission != null) {
      return submission;
    }
    return comments.get(position - (submission == null ? 0 : 1));
  }
}
