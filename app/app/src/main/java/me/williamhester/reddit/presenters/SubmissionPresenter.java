package me.williamhester.reddit.presenters;

import android.os.Parcel;

import java.util.List;

import me.williamhester.reddit.apis.DataCallback;
import me.williamhester.reddit.apis.RedditClient;
import me.williamhester.reddit.models.Submission;

/**
 * Created by william on 6/13/16.
 */
public class SubmissionPresenter extends VotablePresenter {

  private RedditClient redditClient;

  public SubmissionPresenter(RedditClient api) {
    this.redditClient = api;
  }

  @Override
  public void loadMoreSubmissions(final OnLoadedMoreListener callback) {
    String after;
    if (votables.size() == 0) {
      after = null;
    } else {
      after = votables.get(votables.size() - 1).getName();
    }
    redditClient.getSubmissions("", null, after, new DataCallback<List<Submission>>() {
      @Override
      public void onResponse(List<Submission> data) {
        int oldCount = votables.size();
        votables.addAll(data);
        callback.onLoadedMoreVotables(oldCount, votables.size());
      }
    });
  }

  @Override
  public void refreshSubmissions(final OnRefreshListener callback) {
    redditClient.getSubmissions("", null, null, new DataCallback<List<Submission>>() {
      @Override
      public void onResponse(List<Submission> data) {
        votables.clear();
        votables.addAll(data);
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
