package williamhester.me.breadit2.models;

import android.os.Parcel;

/**
 * Created by william on 6/13/16.
 */
public class Submission extends Votable {

  private String domain;
  private String subreddit;
  private String subredditId;
  private String id;
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
//  private boolean edited;
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

  public Submission(SubmissionJson json) {
    domain = json.domain;
    subreddit = json.subreddit;
    subredditId = json.subreddit_id;
    id = json.id;
    name = json.name;
    author = json.author;
    thumbnail = json.thumbnail;
    permalink = json.permalink;
    url = json.url;
    title = json.title;
    postHint = json.post_hint;
    selftextHtml = json.selftext_html;
    linkFlairText = json.link_flair_text;
    authorFlairText = json.author_flair_text;
    distinguished = json.distinguished;
//    edited = json.edited;
    archived = json.archived;
    over18 = json.over_18;
    hidden = json.hidden;
    saved = json.saved;
    stickied = json.stickied;
    isSelf = json.is_self;
    locked = json.locked;
    hideScore = json.hide_score;
    visited = json.visited;
    gilded = json.gilded;
    score = json.score;
    created = json.created;
    createdUtc = json.created_utc;
    numComments = json.num_comments;
    editedUtc = json.edited_utc;
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

//  public boolean isEdited() {
//    return edited;
//  }

  public boolean isArchived() {
    return archived;
  }

  public boolean isNsfw() {
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

  @Override
  public int describeContents() {
    return 1;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.domain);
    dest.writeString(this.subreddit);
    dest.writeString(this.subredditId);
    dest.writeString(this.id);
    dest.writeString(this.name);
    dest.writeString(this.author);
    dest.writeString(this.thumbnail);
    dest.writeString(this.permalink);
    dest.writeString(this.url);
    dest.writeString(this.title);
    dest.writeString(this.postHint);
    dest.writeString(this.selftextHtml);
    dest.writeString(this.linkFlairText);
    dest.writeString(this.authorFlairText);
    dest.writeString(this.distinguished);
//    dest.writeByte(this.edited ? (byte) 1 : (byte) 0);
    dest.writeByte(this.archived ? (byte) 1 : (byte) 0);
    dest.writeByte(this.over18 ? (byte) 1 : (byte) 0);
    dest.writeByte(this.hidden ? (byte) 1 : (byte) 0);
    dest.writeByte(this.saved ? (byte) 1 : (byte) 0);
    dest.writeByte(this.stickied ? (byte) 1 : (byte) 0);
    dest.writeByte(this.isSelf ? (byte) 1 : (byte) 0);
    dest.writeByte(this.locked ? (byte) 1 : (byte) 0);
    dest.writeByte(this.hideScore ? (byte) 1 : (byte) 0);
    dest.writeByte(this.visited ? (byte) 1 : (byte) 0);
    dest.writeInt(this.gilded);
    dest.writeInt(this.score);
    dest.writeInt(this.created);
    dest.writeInt(this.createdUtc);
    dest.writeInt(this.numComments);
    dest.writeInt(this.editedUtc);
  }

  protected Submission(Parcel in) {
    super(in);
    this.domain = in.readString();
    this.subreddit = in.readString();
    this.subredditId = in.readString();
    this.id = in.readString();
    this.name = in.readString();
    this.author = in.readString();
    this.thumbnail = in.readString();
    this.permalink = in.readString();
    this.url = in.readString();
    this.title = in.readString();
    this.postHint = in.readString();
    this.selftextHtml = in.readString();
    this.linkFlairText = in.readString();
    this.authorFlairText = in.readString();
    this.distinguished = in.readString();
//    this.edited = in.readByte() != 0;
    this.archived = in.readByte() != 0;
    this.over18 = in.readByte() != 0;
    this.hidden = in.readByte() != 0;
    this.saved = in.readByte() != 0;
    this.stickied = in.readByte() != 0;
    this.isSelf = in.readByte() != 0;
    this.locked = in.readByte() != 0;
    this.hideScore = in.readByte() != 0;
    this.visited = in.readByte() != 0;
    this.gilded = in.readInt();
    this.score = in.readInt();
    this.created = in.readInt();
    this.createdUtc = in.readInt();
    this.numComments = in.readInt();
    this.editedUtc = in.readInt();
  }

  public static final Creator<Submission> CREATOR = new Creator<Submission>() {
    @Override
    public Submission createFromParcel(Parcel source) {
      return new Submission(source);
    }

    @Override
    public Submission[] newArray(int size) {
      return new Submission[size];
    }
  };
}
