package me.williamhester.reddit.presenters;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import me.williamhester.reddit.apis.DataCallback;
import me.williamhester.reddit.apis.RedditClient;
import me.williamhester.reddit.models.Comment;
import me.williamhester.reddit.models.Submission;

/**
 * Created by william on 6/17/16.
 */
public class CommentsPresenter {

  private Submission submission;
  private final List<Comment> comments = new ArrayList<>();
  private String permalink;
  private RedditClient redditClient;

  public CommentsPresenter(RedditClient api, String permalink, Submission s) {
    this.redditClient = api;
    this.permalink = permalink;
    this.submission = s;
  }

  public void load(final OnCommentsLoadedListener callback) {
    redditClient.getComments(permalink, new DataCallback<Pair<Submission, List<Comment>>>() {
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
