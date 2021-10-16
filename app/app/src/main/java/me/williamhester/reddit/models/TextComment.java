package me.williamhester.reddit.models;

import android.os.Parcel;

import java.util.ArrayList;

import static me.williamhester.reddit.util.UtilKt.unescapeHtml;

/** A regular Reddit comment. */
public class TextComment extends Comment {

  private String subredditId;
  private String linkId;
  private String saved;
  private String id;
  private String author;
  private String parentId;
  private String body;
  private String bodyHtml;
  private String subreddit;
  private String name;
  private String authorFlairText;
  private String distinguished;
//  private VoteStatus likes;
//  private Edited edited;
  private int gilded;
  private int score;
  private int created;
  private int createdUtc;
  private boolean archived;
  private boolean scoreHidden;
  private boolean stickied;
  private ArrayList<Comment> children;

  public TextComment(TextCommentJson json, int level) {
    super(level);
    this.subredditId = json.subreddit_id;
    this.linkId = json.link_id;
    this.saved = json.saved;
    this.id = json.id;
    this.author = json.author;
    this.parentId = json.parent_id;
    this.body = json.body;
    this.bodyHtml = unescapeHtml(json.body_html);
    this.subreddit = json.subreddit;
    this.name = json.name;
    this.authorFlairText = unescapeHtml(json.author_flair_text);
    this.createdUtc = json.created_utc;
    this.distinguished = json.distinguished;
//    this.likes = json.likes;
//    this.edited = json.edited;
    this.gilded = json.gilded;
    this.score = json.score;
    this.created = json.created;
    this.archived = json.archived;
    this.scoreHidden = json.score_hidden;
    this.stickied = json.stickied;
  }

  public String getSubredditId() {
    return subredditId;
  }

  public String getLinkId() {
    return linkId;
  }

  public String getSaved() {
    return saved;
  }

  public String getId() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public String getParentId() {
    return parentId;
  }

  public String getBody() {
    return body;
  }

  public String getBodyHtml() {
    return bodyHtml;
  }

  public String getSubreddit() {
    return subreddit;
  }

  @Override
  public String getName() {
    return name;
  }

  public String getAuthorFlairText() {
    return authorFlairText;
  }

  public String getDistinguished() {
    return distinguished;
  }

//  public VoteStatus getLikes() {
//    return likes;
//  }
//
//  public Edited getEdited() {
//    return edited;
//  }

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

  public boolean isArchived() {
    return archived;
  }

  public boolean isScoreHidden() {
    return scoreHidden;
  }

  public boolean isStickied() {
    return stickied;
  }

  public ArrayList<Comment> getChildren() {
    return children;
  }

  public void setChildren(ArrayList<Comment> children) {
    this.children = children;
  }

  public boolean isHidden() {
    return children != null;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.subredditId);
    dest.writeString(this.linkId);
    dest.writeString(this.saved);
    dest.writeString(this.id);
    dest.writeString(this.author);
    dest.writeString(this.parentId);
    dest.writeString(this.body);
    dest.writeString(this.bodyHtml);
    dest.writeString(this.subreddit);
    dest.writeString(this.name);
    dest.writeString(this.authorFlairText);
    dest.writeString(this.distinguished);
//    dest.writeParcelable(this.likes, flags);
//    dest.writeParcelable(this.edited, flags);
    dest.writeInt(this.gilded);
    dest.writeInt(this.score);
    dest.writeInt(this.created);
    dest.writeInt(this.createdUtc);
    dest.writeByte(this.archived ? (byte) 1 : (byte) 0);
    dest.writeByte(this.scoreHidden ? (byte) 1 : (byte) 0);
    dest.writeByte(this.stickied ? (byte) 1 : (byte) 0);
  }

  protected TextComment(Parcel in) {
    super(in);
    this.subredditId = in.readString();
    this.linkId = in.readString();
    this.saved = in.readString();
    this.id = in.readString();
    this.author = in.readString();
    this.parentId = in.readString();
    this.body = in.readString();
    this.bodyHtml = in.readString();
    this.subreddit = in.readString();
    this.name = in.readString();
    this.authorFlairText = in.readString();
    this.distinguished = in.readString();
//    this.likes = in.readParcelable(VoteStatus.class.getClassLoader());
//    this.edited = in.readParcelable(Edited.class.getClassLoader());
    this.gilded = in.readInt();
    this.score = in.readInt();
    this.created = in.readInt();
    this.createdUtc = in.readInt();
    this.archived = in.readByte() != 0;
    this.scoreHidden = in.readByte() != 0;
    this.stickied = in.readByte() != 0;
  }

  public static final Creator<TextComment> CREATOR = new Creator<TextComment>() {
    @Override
    public TextComment createFromParcel(Parcel source) {
      return new TextComment(source);
    }

    @Override
    public TextComment[] newArray(int size) {
      return new TextComment[size];
    }
  };
}
