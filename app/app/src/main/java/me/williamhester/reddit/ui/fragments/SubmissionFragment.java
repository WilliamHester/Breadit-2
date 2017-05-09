package me.williamhester.reddit.ui.fragments;

import android.os.Bundle;

/** A fragment that shows Submissions */
public class SubmissionFragment extends VotableFragment {

  public static SubmissionFragment newInstance() {
    Bundle args = new Bundle();
    SubmissionFragment fragment = new SubmissionFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected void loadContent() {
    redditClient.getSubmissions("", null, getAfter());
  }
}
