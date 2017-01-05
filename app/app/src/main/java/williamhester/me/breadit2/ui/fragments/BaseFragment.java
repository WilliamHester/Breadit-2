package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import williamhester.me.breadit2.BreaditApplication;
import williamhester.me.breadit2.apis.RedditApi;

/**
 * Created by william on 6/12/16.
 */
public abstract class BaseFragment extends Fragment {

  @Inject protected RedditApi api;

  private Unbinder unbinder;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    BreaditApplication app = (BreaditApplication) getActivity().getApplicationContext();
    app.getApiComponent().inject(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    unbinder = ButterKnife.bind(this, view);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    unbinder.unbind();
  }
}
