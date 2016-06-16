package williamhester.me.breadit2.presenters;

import android.os.Parcel;
import android.os.Parcelable;
import williamhester.me.breadit2.models.Votable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william on 6/13/16.
 */
public abstract class VotablePresenter extends RedditPresenter implements Parcelable {

  protected static final int SUBMISSION = 1;

  private String mTitle;

  protected final ArrayList<Votable> mVotables = new ArrayList<>();

  public abstract void loadMoreSubmissions(OnLoadedMoreListener callback);

  public abstract void refreshSubmissions(OnRefreshListener callback);

  public List<Votable> getVotables() {
    return mVotables;
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
    dest.writeString(this.mTitle);
    dest.writeTypedList(this.mVotables);
  }

  public VotablePresenter() {
  }

  protected VotablePresenter(Parcel in) {
    this.mTitle = in.readString();
    this.mVotables.addAll(in.createTypedArrayList(Votable.CREATOR));
  }

}
