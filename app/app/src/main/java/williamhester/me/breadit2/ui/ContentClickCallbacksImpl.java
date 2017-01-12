package williamhester.me.breadit2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.customtabs.CustomTabsIntent;

import com.squareup.otto.Bus;

import williamhester.me.breadit2.events.StartActivityEvent;
import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.ui.activities.ContentActivity;

import static williamhester.me.breadit2.ui.activities.ContentActivity.COMMENTS;
import static williamhester.me.breadit2.ui.activities.ContentActivity.PERMALINK_EXTRA;
import static williamhester.me.breadit2.ui.activities.ContentActivity.TYPE_EXTRA;
import static williamhester.me.breadit2.ui.activities.ContentActivity.VOTABLE_EXTRA;

/** Callbacks called when a link is clicked. */
public class ContentClickCallbacksImpl implements ContentClickCallbacks {
  private final Bus bus;

  public ContentClickCallbacksImpl(Bus bus) {
    this.bus = bus;
  }

  @Override
  public void onLinkClicked(final Link link) {
    final CustomTabsIntent customTab = new CustomTabsIntent.Builder().build();
    bus.post(new StartActivityEvent() {
      @Override
      public void startActivity(Context context) {
        customTab.launchUrl(context, link.getUri());
      }
    });
  }

  @Override
  public void naviageTo(Parcelable parcelable) {
    if (!(parcelable instanceof Submission)) {
      return;
    }
    Submission submission = (Submission) parcelable;

    final Bundle args = new Bundle();
    args.putParcelable(VOTABLE_EXTRA, parcelable);
    args.putString(TYPE_EXTRA, COMMENTS);
    args.putString(PERMALINK_EXTRA, submission.getPermalink());

    bus.post(new StartActivityEvent() {
      @Override
      public void startActivity(Context context) {
        final Intent i = new Intent(context, ContentActivity.class);
        i.putExtras(args);
        context.startActivity(i);
      }
    });
  }
}
