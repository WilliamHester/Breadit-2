package williamhester.me.breadit2.presenters;

import android.os.Parcel;

import java.util.List;

import williamhester.me.breadit2.apis.DataCallback;
import williamhester.me.breadit2.models.Submission;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionPresenter extends VotablePresenter {

  public SubmissionPresenter() {

  }

  @Override
  public void loadMoreSubmissions(final OnLoadedMoreListener callback) {
    mRedditApi.getSubmissions("", null, null, new DataCallback<List<Submission>>() {
      @Override
      public void onResponse(List<Submission> data) {
        int oldCount = mVotables.size();
        mVotables.addAll(data);
        callback.onLoadedMoreVotables(oldCount, mVotables.size());
      }
    });
  }

  @Override
  public void refreshSubmissions(final OnRefreshListener callback) {
    mRedditApi.getSubmissions("", null, null, new DataCallback<List<Submission>>() {
      @Override
      public void onResponse(List<Submission> data) {
        mVotables.clear();
        mVotables.addAll(data);
        callback.onRefreshedVotables(true);
      }
    });
  }

  @Override
  public int describeContents() {
    return SUBMISSION;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
  }

  protected SubmissionPresenter(Parcel in) {
    super(in);
  }

  public static final Creator<SubmissionPresenter> CREATOR = new Creator<SubmissionPresenter>() {
    @Override
    public SubmissionPresenter createFromParcel(Parcel source) {
      return new SubmissionPresenter(source);
    }

    @Override
    public SubmissionPresenter[] newArray(int size) {
      return new SubmissionPresenter[size];
    }
  };
}
