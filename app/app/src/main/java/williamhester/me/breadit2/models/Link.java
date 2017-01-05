package williamhester.me.breadit2.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by william on 7/19/14.
 */
public class Link implements Parcelable {

  public static final int NOT_SPECIAL = 0;
  public static final int IMGUR_IMAGE = 1;
  public static final int IMGUR_ALBUM = 2;
  public static final int IMGUR_GALLERY = 3;
  public static final int YOUTUBE = 4;
  public static final int NORMAL_IMAGE = 6;
  public static final int SUBMISSION = 7;
  public static final int SUBREDDIT = 8;
  public static final int USER = 9;
  public static final int REDDIT_LIVE = 10;
  public static final int GFYCAT_LINK = 11;
  public static final int GIF = 12;
  public static final int DIRECT_GFY = 13;
  public static final int MESSAGES = 14;
  public static final int COMPOSE = 15;

  private Uri uri;
  private String url;
  private String id;
  private int type;
  private int subtype;

  public Link(String url) {
    this.url = url;
    this.uri = Uri.parse(url);
    if (this.url.charAt(0) == '/') {
      if (this.url.charAt(1) == 'u') { // go to a user
        id = this.url.substring(this.url.indexOf("/u/") + 3);
        type = USER;
      } else if (this.url.charAt(1) == 'r') { // go to a subreddit
        id = url.substring(3);
        type = SUBREDDIT;
      }
    } else {
      try {
        if (uri.getHost().contains("reddit.com")) {
          generateRedditDetails();
        } else if (uri.getHost().contains("imgur")) {
          generateImgurDetails();
        } else if (uri.getHost().contains("youtu.be")
            || uri.getHost().contains("youtube.com")) {
          generateYoutubeDetails();
        } else if (isDirectImageLink()) {
          type = NORMAL_IMAGE;
        } else if (isGif()) {
          type = GIF;
        } else if (uri.getHost().contains("livememe.com")) {
          type = NORMAL_IMAGE;
          generateLiveMemeDetails();
        } else if (uri.getHost().contains("imgflip.com")) {
          type = NORMAL_IMAGE;
          generateImgFlipDetails();
        } else if (this.url.contains("gfycat.com")) {
          generateGfycatDetails();
        } else {
          type = NOT_SPECIAL;
        }
      } catch (NullPointerException e) {
        type = NOT_SPECIAL;
      }
    }
  }

  private void generateImgurDetails() {
    int end = url.indexOf('.', url.indexOf(".com") + 4);
    end = end != -1 ? end : url.length();
    int start = end - 1;
    while (url.charAt(start) != '/') {
      start--;
    }
    id = url.substring(start + 1, end);
    while (url.charAt(start) == '/') {
      start--;
    }
    char c = url.charAt(start);
    switch (c) {
      case 'm': // imgur.com
        type = IMGUR_IMAGE;
        break;
      case 'a': // imgur.com/a/
        type = IMGUR_ALBUM;
        break;
      case 'y': // imgur.com/gallery/
        type = IMGUR_GALLERY;
        break;
    }
  }

  private void generateYoutubeDetails() {
    if (uri.getHost().equals("youtu.be")) {
      id = uri.getPathSegments().get(0);
    } else {
      id = uri.getQueryParameter("v");
    }
    type = YOUTUBE;
    url = "http://img.youtube.com/vi/" + id + "/0.jpg";
  }

  private void generateLiveMemeDetails() {
    url += ".jpg";
  }

  private void generateImgFlipDetails() {
    int start = url.length() - 2;
    while (url.charAt(start - 1) != '/') {
      start--;
    }
    id = url.substring(start);
    url = "http://i.imgflip.com/" + id + ".jpg";
  }

  private boolean isDirectImageLink() {
    String lps = uri.getLastPathSegment();
    if (lps == null) {
      return false;
    }
    String suffix = url.substring(url.indexOf(".", url.indexOf(lps)) + 1);
    return suffix.equalsIgnoreCase("png") || suffix.equalsIgnoreCase("jpg")
        || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("bmp");
  }

  private boolean isGif() {
    String lps = uri.getLastPathSegment();
    if (lps == null) {
      return false;
    }
    String suffix = url.substring(url.indexOf(".", url.indexOf(lps)) + 1);
    return suffix.equalsIgnoreCase("gif");
  }

  private void generateRedditDetails() {
    int i = url.indexOf("/", url.indexOf("reddit.com/r/") + 13);
    if (i == -1 || i == url.length()) { // definitely a subreddit or the frontpage
      int slashR = url.indexOf("/r/");
      if (slashR != -1) {
        id = url.substring(slashR + 3, i == -1 ? url.length() : i);
      } else {
        id = "";
      }
      type = SUBREDDIT;
    } else if (url.toLowerCase().contains("/live/")) {
      id = url.substring(url.indexOf("/live/") + 6);
      type = REDDIT_LIVE;
    } else if (url.contains("/user/")) {
      id = url.substring(url.indexOf("/user/") + 6);
      type = USER;
    } else { // found a link to another post
      int r = url.indexOf("/r/");
      if (r != -1) {
        id = url.substring(url.indexOf("/r/"));
        type = SUBMISSION;
      } else if (url.contains("reddit.com/message/")) {
        id = url.substring(url.indexOf("reddit.com/message/") + 19);
        int slash = id.indexOf('/');
        id = id.substring(0, slash == -1 ? id.length() : slash).toLowerCase();
        if (id.equals("inbox") || id.equals("unread") || id.equals("messages")
            || id.equals("comments") || id.equals("selfreply") || id.equals("sent")
            || id.equals("moderator")) {
          type = MESSAGES;
        } else if (id.equals("compose")) {
          type = COMPOSE;
        } else {
          type = NOT_SPECIAL;
          id = null;
        }
      } else {
        type = NOT_SPECIAL;
        id = null;
      }
    }
  }

  private void generateGfycatDetails() {
    if (url.contains("zippy") || url.contains("fat") || url.contains("giant")) {
      type = DIRECT_GFY;
      return;
    }
    int start = url.toLowerCase().indexOf("gfycat.com/");
    if (start < 0) {
      type = NOT_SPECIAL;
      return;
    }
    start += 11;
    int end = url.indexOf(".", start);
    if (end < 0) {
      end = url.length();
    }
    id = url.substring(start, end);
    type = GFYCAT_LINK;
  }

  public String getLinkId() {
    return id;
  }

  public int getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }

  public Uri getUri() {
    return uri;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.uri, 0);
    dest.writeString(this.url);
    dest.writeString(this.id);
    dest.writeInt(this.type);
    dest.writeInt(this.subtype);
  }

  private Link(Parcel in) {
    this.uri = in.readParcelable(Uri.class.getClassLoader());
    this.url = in.readString();
    this.id = in.readString();
    this.type = in.readInt();
    this.subtype = in.readInt();
  }

  public static final Creator<Link> CREATOR = new Creator<Link>() {
    public Link createFromParcel(Parcel source) {
      return new Link(source);
    }

    public Link[] newArray(int size) {
      return new Link[size];
    }
  };
}
