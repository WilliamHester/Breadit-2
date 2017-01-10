package williamhester.me.breadit2.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.ui.fragments.CommentsFragment;

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
}
