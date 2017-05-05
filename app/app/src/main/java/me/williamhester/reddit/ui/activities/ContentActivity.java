package me.williamhester.reddit.ui.activities;

import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.Subscribe;

import me.williamhester.reddit.R;
import me.williamhester.reddit.messages.FailedRedditRequestMessage;
import me.williamhester.reddit.models.Submission;
import me.williamhester.reddit.ui.fragments.CommentsFragment;

/** Activity that holds basic content. */
public class ContentActivity extends BaseActivity {

  public static final String TYPE_EXTRA = "type";
  public static final String VOTABLE_EXTRA = "votable";
  public static final String PERMALINK_EXTRA = "permalink";

  public static final String COMMENTS = "comments";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    if (f == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, createContentFragment())
          .commit();
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_content;
  }

  protected Fragment createContentFragment() {
    Bundle args = getIntent().getExtras();
    String type = args.getString(TYPE_EXTRA);
    if (type == null) {
      return null;
    }
    switch (type) {
      case COMMENTS:
        String permalink = args.getString(PERMALINK_EXTRA);
        Submission s = args.getParcelable(VOTABLE_EXTRA);
        return CommentsFragment.newInstance(permalink, s);
      default:
        return null;
    }
  }

  @Subscribe
  public void onRequestFailure(FailedRedditRequestMessage message) {
    Snackbar
        .make(
            findViewById(R.id.fragment_container),
            R.string.request_failed,
            BaseTransientBottomBar.LENGTH_SHORT)
        .show();
  }
}
