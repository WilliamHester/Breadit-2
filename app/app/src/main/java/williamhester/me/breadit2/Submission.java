package williamhester.me.breadit2;

import com.google.gson.JsonObject;

/**
 * Created by william on 6/13/16.
 */
public class Submission extends Votable {

  private String domain;
  private String subreddit;
  private String subredditId;
  private String id;
  private String name;
  private String author;
  private String thumbnail;
  private String permalink;
  private String url;
  private String title;
  private String postHint;
  private String selftextHtml;
  private String linkFlairText;
  private String authorFlairText;
  private String distinguished;
  private boolean edited;
  private boolean archived;
  private boolean over18;
  private boolean hidden;
  private boolean saved;
  private boolean stickied;
  private boolean isSelf;
  private boolean locked;
  private boolean hideScore;
  private boolean visited;
  private int gilded;
  private int score;
  private int created;
  private int createdUtc;
  private int numComments;
  private int editedUtc;
//  private Link link;
//  private VoteStatus voteStatus;

  public Submission(JsonObject json) {
    domain = json.get("domain").getAsString();
    subreddit = json.get("subreddit").getAsString();
    subredditId = json.get("subreddit_id").getAsString();
    id = json.get("id").getAsString();
    name = json.get("name").getAsString();
    author = json.get("author").getAsString();
    thumbnail = json.get("thumbnail").getAsString();
    permalink = json.get("permalink").getAsString();
    url = json.get("url").getAsString();
    title = json.get("title").getAsString();
    postHint = json.get("post_hint").getAsString();
    selftextHtml = json.get("selftext_html").getAsString();
    linkFlairText = json.get("link_flair_text").getAsString();
    authorFlairText = json.get("author_flair_text").getAsString();
    distinguished = json.get("distinguished").getAsString();
    edited = json.get("edited").getAsBoolean();
    archived = json.get("archived").getAsBoolean();
    over18 = json.get("over_18").getAsBoolean();
    hidden = json.get("hidden").getAsBoolean();
    saved = json.get("saved").getAsBoolean();
    stickied = json.get("stickied").getAsBoolean();
    isSelf = json.get("is_self").getAsBoolean();
    locked = json.get("locked").getAsBoolean();
    hideScore = json.get("hide_score").getAsBoolean();
    visited = json.get("visited").getAsBoolean();
    gilded = json.get("gilded").getAsInt();
    score = json.get("score").getAsInt();
    created = json.get("created").getAsInt();
    createdUtc = json.get("created_utc").getAsInt();
    numComments = json.get("num_comments").getAsInt();
    editedUtc = json.get("edited_utc").getAsInt();
//    link = json.get("link").getAsString();
//    voteStatus = json.get("voteStatus").getAsString();
  }

  public String getDomain() {
    return domain;
  }

  public String getSubreddit() {
    return subreddit;
  }

  public String getSubredditId() {
    return subredditId;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAuthor() {
    return author;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public String getPermalink() {
    return permalink;
  }

  public String getUrl() {
    return url;
  }

  public String getTitle() {
    return title;
  }

  public String getPostHint() {
    return postHint;
  }

  public String getSelftextHtml() {
    return selftextHtml;
  }

  public String getLinkFlairText() {
    return linkFlairText;
  }

  public String getAuthorFlairText() {
    return authorFlairText;
  }

  public String getDistinguished() {
    return distinguished;
  }

  public boolean isEdited() {
    return edited;
  }

  public boolean isArchived() {
    return archived;
  }

  public boolean isOver18() {
    return over18;
  }

  public boolean isHidden() {
    return hidden;
  }

  public boolean isSaved() {
    return saved;
  }

  public boolean isStickied() {
    return stickied;
  }

  public boolean isSelf() {
    return isSelf;
  }

  public boolean isLocked() {
    return locked;
  }

  public boolean isHideScore() {
    return hideScore;
  }

  public boolean isVisited() {
    return visited;
  }

  public int getGilded() {
    return gilded;
  }

  public int getScore() {
    return score;
  }

  public int getCreated() {
    return created;
  }

  public int getCreatedUtc() {
    return createdUtc;
  }

  public int getNumComments() {
    return numComments;
  }

  public int getEditedUtc() {
    return editedUtc;
  }
}
