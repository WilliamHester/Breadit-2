package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import williamhester.me.breadit2.apis.RedditApi;
import williamhester.me.breadit2.models.Comment;
import williamhester.me.breadit2.models.MoreComment;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.models.TextComment;
import williamhester.me.breadit2.models.Votable;
import williamhester.me.breadit2.presenters.CommentsPresenter;
import williamhester.me.breadit2.ui.adapters.CommentsAdapter;

/**
 * Created by william on 6/17/16.
 */
public class CommentsFragment extends ContentFragment<CommentsPresenter, CommentsAdapter> {

  private static final String PERMALINK = "permalink";
  private static final String SUBMISSION = "submission";

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

    contentPresenter.load(new CommentsPresenter.OnCommentsLoadedListener() {
      @Override
      public void onCommentsLoaded() {
        adapter.setSubmission(contentPresenter.getSubmission());
        adapter.notifyDataSetChanged();
      }
    });
  }

  @Override
  protected CommentsPresenter createPresenter(RedditApi api) {
    String permalink = getArguments().getString(PERMALINK);
    Submission submission = getArguments().getParcelable(SUBMISSION);
    return new CommentsPresenter(api, permalink, submission);
  }

  @Override
  protected CommentsAdapter createAdapter(Bundle savedInstanceState) {
    return new CommentsAdapter(getLayoutInflater(savedInstanceState), htmlParser,
        contentClickCallbacks, this, contentPresenter.getSubmission(),
        contentPresenter.getComments());
  }

  @Override
  public void onVotableClicked(Votable votable) {
    if (votable instanceof TextComment) {
      int position = contentPresenter.getComments().indexOf(votable) + 1;
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

  private void collapseComment(int position, TextComment comment) {
    ArrayList<Comment> collapsedComments = new ArrayList<>();
    List<Comment> comments = contentPresenter.getComments();

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
    List<Comment> comments = contentPresenter.getComments().subList(position - 1, position);
    comments.addAll(comment.getChildren());
    int itemCount = comment.getChildren().size();
    comment.setChildren(null);

    adapter.notifyItemChanged(position);
    adapter.notifyItemRangeInserted(position + 1, itemCount);
  }
}
