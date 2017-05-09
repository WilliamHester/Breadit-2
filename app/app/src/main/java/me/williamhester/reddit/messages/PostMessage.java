package me.williamhester.reddit.messages;

import me.williamhester.reddit.models.Post;

/**
 * Created by williamhester on 5/8/17.
 */
public class PostMessage {
  private final Post post;

  public PostMessage(Post post) {
    this.post = post;
  }

  public Post getPost() {
    return post;
  }
}
