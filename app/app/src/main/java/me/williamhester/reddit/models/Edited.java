package me.williamhester.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/** Class used for Reddit's weird edited rules */
public class Edited implements Parcelable {

  private boolean isEdited;
  private long editTime;

  private Edited(boolean isEdited, long editTime) {
    this.isEdited = isEdited;
    this.editTime = editTime;
  }

  public boolean isEdited() {
    return isEdited;
  }

  public long getEditTime() {
    return editTime;
  }

  public static class TypeAdapter extends com.google.gson.TypeAdapter {
    @Override
    public void write(JsonWriter out, Object value) throws IOException {}

    @Override
    public Object read(JsonReader in) throws IOException {
      switch (in.peek()) {
        case BOOLEAN:
          return new Edited(in.nextBoolean(), -1);
        case NUMBER:
          return new Edited(true, in.nextLong());
        case NULL:
        default:
          return null;
      }
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte(this.isEdited ? (byte) 1 : (byte) 0);
    dest.writeLong(this.editTime);
  }

  protected Edited(Parcel in) {
    this.isEdited = in.readByte() != 0;
    this.editTime = in.readLong();
  }

  public static final Creator<Edited> CREATOR = new Creator<Edited>() {
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
