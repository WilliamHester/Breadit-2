package williamhester.me.breadit2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by william on 6/13/16.
 */
public class Votable implements Parcelable {

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }

  public Votable() {
  }

  protected Votable(Parcel in) {
  }

  public static final Creator<Votable> CREATOR = new Creator<Votable>() {
    @Override
    public Votable createFromParcel(Parcel source) {
      return new Votable(source);
    }

    @Override
    public Votable[] newArray(int size) {
      return new Votable[size];
    }
  };
}
