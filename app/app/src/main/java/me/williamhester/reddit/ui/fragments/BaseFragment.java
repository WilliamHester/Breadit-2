package me.williamhester.reddit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.williamhester.reddit.BreaditApplication;
import me.williamhester.reddit.apis.RedditClient;
import me.williamhester.reddit.html.HtmlParser;
import me.williamhester.reddit.ui.ContentClickCallbacks;

/**
 * Created by william on 6/12/16.
 */
public abstract class BaseFragment extends Fragment {

  @Inject protected RedditClient api;
  @Inject protected HtmlParser htmlParser;
  @Inject protected ContentClickCallbacks contentClickCallbacks;

  private Unbinder unbinder;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    BreaditApplication app = (BreaditApplication) getActivity().getApplicationContext();
    app.getApplicationComponent().inject(this);
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
    unbinder = null;
  }
}
