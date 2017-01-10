package williamhester.me.breadit2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.customtabs.CustomTabsIntent;

import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.models.Votable;
import williamhester.me.breadit2.ui.activities.ContentActivity;

import static williamhester.me.breadit2.ui.activities.ContentActivity.COMMENTS;
import static williamhester.me.breadit2.ui.activities.ContentActivity.PERMALINK_EXTRA;
import static williamhester.me.breadit2.ui.activities.ContentActivity.TYPE_EXTRA;
import static williamhester.me.breadit2.ui.activities.ContentActivity.VOTABLE_EXTRA;

/** Callbacks called when a link is clicked. */
public class ContentClickCallbacksImpl implements ContentClickCallbacks {
  private final Context context;

  public ContentClickCallbacksImpl(Context context) {
    this.context = context;
  }

  @Override
  public void onLinkClicked(Link link) {
    CustomTabsIntent customTab = new CustomTabsIntent.Builder().build();
    customTab.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    customTab.launchUrl(context, link.getUri());
  }

  @Override
  public void naviageTo(Parcelable parcelable) {
    if (!(parcelable instanceof Submission)) {
      return;
    }
    Submission submission = (Submission) parcelable;

    Bundle args = new Bundle();
    args.putParcelable(VOTABLE_EXTRA, parcelable);
    args.putString(TYPE_EXTRA, COMMENTS);
    args.putString(PERMALINK_EXTRA, submission.getPermalink());

    Intent i = new Intent(context, ContentActivity.class);
    i.putExtras(args);
    context.startActivity(i);
  }
}
