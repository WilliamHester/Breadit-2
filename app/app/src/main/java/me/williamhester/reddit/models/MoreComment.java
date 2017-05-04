package me.williamhester.reddit.models;

import android.os.Parcel;

import java.util.List;

/**
 * Created by william on 6/16/16.
 */
public class MoreComment extends Comment {

  private String parentId;
  private List<String> children;

  public MoreComment(MoreCommentJson json, int level) {
    super(level);
    parentId = json.parent_id;
    children = json.children;
  }

  public String getParentId() {
    return parentId;
  }

  public List<String> getChildren() {
    return children;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.parentId);
    dest.writeStringList(this.children);
  }

  protected MoreComment(Parcel in) {
    super(in);
    this.parentId = in.readString();
    this.children = in.createStringArrayList();
  }

  public static final Creator<MoreComment> CREATOR = new Creator<MoreComment>() {
    @Override
    public MoreComment createFromParcel(Parcel source) {
      return new MoreComment(source);
    }

    @Override
    public MoreComment[] newArray(int size) {
      return new MoreComment[size];
    }
  };
}
