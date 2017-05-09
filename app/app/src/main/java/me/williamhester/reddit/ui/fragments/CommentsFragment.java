package me.williamhester.reddit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.williamhester.reddit.messages.PostMessage;
import me.williamhester.reddit.models.Comment;
import me.williamhester.reddit.models.MoreComment;
import me.williamhester.reddit.models.Post;
import me.williamhester.reddit.models.Submission;
import me.williamhester.reddit.models.TextComment;
import me.williamhester.reddit.models.Votable;
import me.williamhester.reddit.ui.adapters.CommentsAdapter;

/**
 * Created by william on 6/17/16.
 */
public class CommentsFragment extends ContentFragment<CommentsAdapter> {

  private static final String PERMALINK = "permalink";
  private static final String SUBMISSION = "submission";

  private String permalink;
  private Submission submission;
  private final ArrayList<Comment> comments = new ArrayList<>();

  public static CommentsFragment newInstance(String permalink, Submission s) {
    Bundle args = new Bundle();
    args.putString(PERMALINK, permalink);
    args.putParcelable(SUBMISSION, s);
    CommentsFragment fragment = new CommentsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    permalink = getArguments().getString(PERMALINK);
    submission = getArguments().getParcelable(SUBMISSION);

    loadContent();
  }

  @Override
  protected void loadContent() {
    getRedditClient().getComments(permalink);
  }

  @Override
  protected CommentsAdapter createAdapter(Bundle savedInstanceState) {
    return new CommentsAdapter(getLayoutInflater(savedInstanceState), htmlParser,
        contentClickCallbacks, this, submission, comments);
  }

  @Override
  public void onVotableClicked(Votable votable) {
    if (votable instanceof TextComment) {
      int position = comments.indexOf(votable) + 1;
      TextComment comment = (TextComment) votable;
      if (comment.isHidden()) {
        expandComment(position, comment);
      } else {
        collapseComment(position, comment);
      }
      return;
    }
    if (votable instanceof MoreComment) {
      // TODO: Load more comments
      return;
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onCommentsLoaded(PostMessage message) {
    setLoading(false);
    Post post = message.getPost();
    int oldAdapterCount = adapter.getItemCount();
    submission = post.getSubmission();
    comments.clear();
    comments.addAll(post.getComments());

    adapter.notifyItemRangeInserted(oldAdapterCount, comments.size() + 1 - oldAdapterCount);
    if (oldAdapterCount > 0) {
      adapter.notifyItemRangeChanged(0, oldAdapterCount);
    }
  }

  private void collapseComment(int position, TextComment comment) {
    ArrayList<Comment> collapsedComments = new ArrayList<>();

    int start = position;
    Comment c = comments.get(position);
    while (c.getLevel() > comment.getLevel()) {
      position++;
      if (position < comments.size()) {
        c = comments.get(position);
      } else {
        break;
      }
    }

    List<Comment> subList = comments.subList(start, position);
    collapsedComments.addAll(subList);
    subList.clear();
    comment.setChildren(collapsedComments);
    adapter.notifyItemChanged(start);
    adapter.notifyItemRangeRemoved(start + 1, position - start);
    recyclerView.scrollToPosition(start);
  }

  private void expandComment(int position, TextComment comment) {
    List<Comment> commentsSubList = comments.subList(position - 1, position);
    commentsSubList.addAll(comment.getChildren());
    int itemCount = comment.getChildren().size();
    comment.setChildren(null);

    adapter.notifyItemChanged(position);
    adapter.notifyItemRangeInserted(position + 1, itemCount);
  }
}
