package me.williamhester.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by william on 6/13/16.
 */
public class Votable implements Parcelable {

  protected String name;

  public static final Creator<Votable> CREATOR = new Creator<Votable>() {
    @Override
    public Votable createFromParcel(Parcel in) {
      return new Votable(in);
    }

    @Override
    public Votable[] newArray(int size) {
      return new Votable[size];
    }
  };

  public String getName() {
    return name;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }

  public Votable() {
  }

  protected Votable(Parcel in) {
    name = in.readString();
  }
}
