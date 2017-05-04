package me.williamhester.reddit.models;

import java.util.List;

/** A tuple containing the {@link Submission} and its {@link List<Comment>}. */
public class Post {
  private Submission submission;
  private List<Comment> comments;

  public Post(Submission submission, List<Comment> comments) {
    this.submission = submission;
    this.comments = comments;
  }

  public Submission getSubmission() {
    return submission;
  }

  public List<Comment> getComments() {
    return comments;
  }
}
