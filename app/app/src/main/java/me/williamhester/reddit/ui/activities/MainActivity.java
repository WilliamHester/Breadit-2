package me.williamhester.reddit.ui.activities;

import android.support.v4.app.Fragment;

import com.squareup.otto.Subscribe;

import me.williamhester.reddit.R;
import me.williamhester.reddit.events.StartActivityEvent;
import me.williamhester.reddit.ui.fragments.SubmissionFragment;

/**
 * Created by william on 6/17/16.
 */
public class MainActivity extends ContentActivity {

  @Override
  protected Fragment createContentFragment() {
    return SubmissionFragment.newInstance();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  @Subscribe
  public void startActivity(StartActivityEvent request) {
    request.startActivity(this);
  }
}
