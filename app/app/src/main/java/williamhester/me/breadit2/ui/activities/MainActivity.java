package williamhester.me.breadit2.ui.activities;

import android.support.v4.app.Fragment;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.ui.fragments.SubmissionFragment;

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
}
