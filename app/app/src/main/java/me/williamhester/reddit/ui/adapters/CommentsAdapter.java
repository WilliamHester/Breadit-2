package me.williamhester.reddit.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.williamhester.reddit.R;
import me.williamhester.reddit.html.HtmlParser;
import me.williamhester.reddit.models.Comment;
import me.williamhester.reddit.models.MoreComment;
import me.williamhester.reddit.models.Submission;
import me.williamhester.reddit.ui.ContentClickCallbacks;
import me.williamhester.reddit.ui.VotableCallbacks;
import me.williamhester.reddit.ui.viewholders.ContentViewHolder;
import me.williamhester.reddit.ui.viewholders.MoreCommentsViewHolder;
import me.williamhester.reddit.ui.viewholders.SubmissionSelfTextViewHolder;
import me.williamhester.reddit.ui.viewholders.SubmissionViewHolder;

/**
 * An adapter for pages that are showing a comments screen. Keeps the submission at the top and
 * displays the rest of the comments in the regular tree structure.
 */
public class CommentsAdapter extends ContentAdapter {

  private static final int SUBMISSION_SELF = 4;
  private static final int MORE_COMMENTS = 11;

  private final List<Comment> comments;
  private Submission submission;

  public CommentsAdapter(LayoutInflater inflater,
      HtmlParser htmlParser,
      ContentClickCallbacks contentClickCallbacks,
      VotableCallbacks clickListener,
      Submission submission,
      List<Comment> comments) {
    super(inflater, htmlParser, contentClickCallbacks, clickListener);

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
        return new SubmissionSelfTextViewHolder(v, getHtmlParser(), clickListener.get());
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
      if (!submission.getSelftextHtml().isEmpty()) {
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
