package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter.refreshSubmissions(new VotablePresenter.OnRefreshListener() {
      @Override
      public void onRefreshedVotables(boolean isNew) {
        mAdapter.notifyDataSetChanged();
      }
    });
  }

  @Override
  protected VotablePresenter createPresenter() {
    return new SubmissionPresenter();
  }
}
