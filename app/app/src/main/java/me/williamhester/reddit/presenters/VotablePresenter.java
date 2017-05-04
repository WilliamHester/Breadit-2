package me.williamhester.reddit.presenters;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import me.williamhester.reddit.models.Votable;

/**
 * Created by william on 6/13/16.
 */
public abstract class VotablePresenter implements Parcelable {

  protected static final int SUBMISSION = 1;

  private String title;

  protected final ArrayList<Votable> votables = new ArrayList<>();

  public abstract void loadMoreSubmissions(OnLoadedMoreListener callback);

  public abstract void refreshSubmissions(OnRefreshListener callback);

  public List<Votable> getVotables() {
    return votables;
  }

  public interface OnLoadedMoreListener {
    void onLoadedMoreVotables(int oldCount, int newCount);
  }

  public interface OnRefreshListener {
    void onRefreshedVotables(boolean isNew);
  }

  @Override
  public abstract int describeContents();

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.title);
//    dest.writeTypedList(this.votables);
  }

  public VotablePresenter() {
  }

  protected VotablePresenter(Parcel in) {
    this.title = in.readString();
//    this.votables.addAll(in.createTypedArrayList(Votable.CREATOR));
  }
}
