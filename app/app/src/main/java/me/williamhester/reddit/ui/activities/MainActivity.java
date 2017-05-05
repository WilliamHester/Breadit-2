package me.williamhester.reddit.ui.activities;

import android.support.v4.app.Fragment;

import me.williamhester.reddit.R;
import me.williamhester.reddit.ui.fragments.SubmissionFragment;

/** The main activity */
public class MainActivity extends ContentActivity {

  @Override
  protected Fragment createContentFragment() {
    return SubmissionFragment.newInstance();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }
}
