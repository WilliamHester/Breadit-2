package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import williamhester.me.breadit2.presenters.VotablePresenter;
import williamhester.me.breadit2.ui.adapters.ContentAdapter;

/**
 * Created by william on 6/15/16.
 */
public abstract class VotableFragment extends ContentFragment {

  protected VotablePresenter contentPresenter;
  protected ContentAdapter adapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    contentPresenter = createPresenter();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    adapter = new ContentAdapter(contentPresenter.getVotables(), getLayoutInflater(savedInstanceState));
    recyclerView.setAdapter(adapter);
  }
  
  protected abstract VotablePresenter createPresenter();
}
