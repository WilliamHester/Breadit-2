package me.williamhester.reddit.models;

/**  */
public class Subreddit {

  private String displayName;
  private String name;

  public Subreddit(SubredditJson subredditJson) {
    this.displayName = subredditJson.display_name;
    this.name = subredditJson.name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getName() {
    return name;
  }
}
