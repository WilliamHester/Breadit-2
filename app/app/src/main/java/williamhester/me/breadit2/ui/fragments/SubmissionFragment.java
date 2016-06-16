package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;

import williamhester.me.breadit2.presenters.SubmissionPresenter;
import williamhester.me.breadit2.presenters.VotablePresenter;

/**
 * Created by william on 6/15/16.
 */
public class SubmissionFragment extends VotableFragment {

  public static SubmissionFragment newInstance() {
    Bundle args = new Bundle();
    SubmissionFragment fragment = new SubmissionFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected VotablePresenter createPresenter() {
    return new SubmissionPresenter();
  }
}
