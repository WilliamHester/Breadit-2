package williamhester.me.breadit2.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.ui.ContentCallbacks;
import williamhester.me.breadit2.ui.fragments.CommentsFragment;

/**
 * Created by william on 6/17/16.
 */
public class ContentActivity extends BaseActivity implements ContentCallbacks {

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

  @Override
  public void navigateTo(Object object) {
    if (!(object instanceof Parcelable)) {
      return;
    }
    Bundle args = new Bundle();
    args.putParcelable(VOTABLE_EXTRA, (Parcelable) object);
    Intent i = null;
    if (object instanceof Submission) {
      Submission submission = (Submission) object;
      args.putString(TYPE_EXTRA, COMMENTS);
      args.putString(PERMALINK_EXTRA, submission.getPermalink());
      i = new Intent(this, ContentActivity.class);
    }
    if (i != null) {
      i.putExtras(args);
      startActivity(i);
    }

  }

  @Override
  public void showLink(Link link) {

  }
}