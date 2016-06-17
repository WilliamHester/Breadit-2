package williamhester.me.breadit2.presenters;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import williamhester.me.breadit2.apis.DataCallback;
import williamhester.me.breadit2.models.Comment;
import williamhester.me.breadit2.models.Submission;

/**
 * Created by william on 6/17/16.
 */
public class CommentsPresenter extends RedditPresenter {

  private Submission submission;
  private final List<Comment> comments = new ArrayList<>();
  private String permalink;

  public CommentsPresenter(String permalink, Submission s) {
    this.permalink = permalink;
    this.submission = s;
  }

  public void load(final OnCommentsLoadedListener callback) {
    redditApi.getComments(permalink, new DataCallback<Pair<Submission, List<Comment>>>() {
      @Override
      public void onResponse(Pair<Submission, List<Comment>> data) {
        submission = data.first;
        comments.addAll(data.second);
        callback.onCommentsLoaded();
      }
    });
  }

  public Submission getSubmission() {
    return submission;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public interface OnCommentsLoadedListener {
    void onCommentsLoaded();
  }

}
