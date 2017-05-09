package me.williamhester.reddit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

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

  @Inject RedditClient redditClient;
  @Inject HtmlParser htmlParser;
  @Inject ContentClickCallbacks contentClickCallbacks;
  @Inject EventBus eventBus;

  private Unbinder unbinder;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    BreaditApplication app = (BreaditApplication) getActivity().getApplicationContext();
    app.getApplicationComponent().inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();

    eventBus.register(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    unbinder = ButterKnife.bind(this, view);
  }

  @Override
  public void onStop() {
    super.onStop();

    eventBus.unregister(this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    unbinder.unbind();
    unbinder = null;
  }

  protected final RedditClient getRedditClient() {
    return redditClient;
  }

  protected final HtmlParser getHtmlParser() {
    return htmlParser;
  }

  protected final ContentClickCallbacks getContentClickCallbacks() {
    return contentClickCallbacks;
  }
}
