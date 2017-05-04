package me.williamhester.reddit.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.squareup.otto.Subscribe;

import me.williamhester.reddit.R;
import me.williamhester.reddit.events.StartActivityEvent;
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

    SharedPreferences prefs = getSharedPreferences("default", MODE_PRIVATE);
    prefs.edit().putBoolean("test", true).apply();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_content;
  }

  @Override
  @Subscribe
  public void startActivity(StartActivityEvent request) {
    request.startActivity(this);
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
}
