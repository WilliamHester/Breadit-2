package me.williamhester.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by william on 6/13/16.
 */
public class Votable implements Parcelable {

  protected String name;

  public String getName() {
    return name;
  }

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

}
