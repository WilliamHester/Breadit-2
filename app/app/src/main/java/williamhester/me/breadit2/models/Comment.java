package williamhester.me.breadit2.models;

import android.os.Parcel;

/**
 * Created by william on 6/16/16.
 */
public abstract class Comment extends Votable {
  private int level;

  public Comment(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(this.level);
  }

  protected Comment(Parcel in) {
    super(in);
    this.level = in.readInt();
  }
}
