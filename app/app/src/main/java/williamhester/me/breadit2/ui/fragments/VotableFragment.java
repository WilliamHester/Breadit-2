package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import williamhester.me.breadit2.presenters.VotablePresenter;
import williamhester.me.breadit2.ui.adapters.ContentAdapter;
import williamhester.me.breadit2.ui.fragments.ContentFragment;

/**
 * Created by william on 6/15/16.
 */
public abstract class VotableFragment extends ContentFragment {

  protected VotablePresenter mPresenter;
  protected ContentAdapter mAdapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = createPresenter();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mAdapter = new ContentAdapter(mPresenter.getVotables(), getLayoutInflater(savedInstanceState));
    mRecyclerView.setAdapter(mAdapter);
  }
  
  protected abstract VotablePresenter createPresenter();
}
