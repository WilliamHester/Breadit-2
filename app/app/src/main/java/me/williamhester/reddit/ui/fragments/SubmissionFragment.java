package me.williamhester.reddit.ui.fragments;

import android.os.Bundle;

import javax.inject.Inject;

import me.williamhester.reddit.apis.RedditClient;
import me.williamhester.reddit.presenters.SubmissionPresenter;
import me.williamhester.reddit.presenters.VotablePresenter;

/**
 * Created by william on 6/15/16.
 */
public class SubmissionFragment extends VotableFragment {

  @Inject
  RedditClient api;

  public static SubmissionFragment newInstance() {
    Bundle args = new Bundle();
    SubmissionFragment fragment = new SubmissionFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected VotablePresenter createPresenter(RedditClient api) {
    return new SubmissionPresenter(api);
  }
}
