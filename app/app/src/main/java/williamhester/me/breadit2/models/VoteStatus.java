package williamhester.me.breadit2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by william on 6/16/16.
 */
public class VoteStatus implements Parcelable {
  private static final int UPVOTED = 1;
  private static final int NEUTRAL = 0;
  private static final int DOWNVOTED = -1;

  private int value;

  public void setValue(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.value);
  }

  public VoteStatus() {
  }

  protected VoteStatus(Parcel in) {
    this.value = in.readInt();
  }

  public static final Parcelable.Creator<VoteStatus> CREATOR = new Parcelable.Creator<VoteStatus>() {
    @Override
    public VoteStatus createFromParcel(Parcel source) {
      return new VoteStatus(source);
    }

    @Override
    public VoteStatus[] newArray(int size) {
      return new VoteStatus[size];
    }
  };
}
