package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import williamhester.me.breadit2.apis.RedditApi;
import williamhester.me.breadit2.presenters.SubmissionPresenter;
import williamhester.me.breadit2.presenters.VotablePresenter;

/**
 * Created by william on 6/15/16.
 */
public class SubmissionFragment extends VotableFragment {

  @Inject RedditApi api;

  public static SubmissionFragment newInstance() {
    Bundle args = new Bundle();
    SubmissionFragment fragment = new SubmissionFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected VotablePresenter createPresenter(RedditApi api) {
    return new SubmissionPresenter(api);
  }
}
