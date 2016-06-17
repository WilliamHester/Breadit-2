package williamhester.me.breadit2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by william on 6/16/16.
 */
public class Edited implements Parcelable {
  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }

  public Edited() {
  }

  protected Edited(Parcel in) {
  }

  public static final Parcelable.Creator<Edited> CREATOR = new Parcelable.Creator<Edited>() {
    @Override
    public Edited createFromParcel(Parcel source) {
      return new Edited(source);
    }

    @Override
    public Edited[] newArray(int size) {
      return new Edited[size];
    }
  };
}
