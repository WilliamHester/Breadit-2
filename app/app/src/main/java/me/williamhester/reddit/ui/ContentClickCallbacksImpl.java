package me.williamhester.reddit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.customtabs.CustomTabsIntent;

import org.greenrobot.eventbus.EventBus;

import me.williamhester.reddit.messages.StartActivityMessage;
import me.williamhester.reddit.models.Link;
import me.williamhester.reddit.models.Submission;
import me.williamhester.reddit.ui.activities.ContentActivity;

import static me.williamhester.reddit.ui.activities.ContentActivity.COMMENTS;
import static me.williamhester.reddit.ui.activities.ContentActivity.PERMALINK_EXTRA;
import static me.williamhester.reddit.ui.activities.ContentActivity.TYPE_EXTRA;
import static me.williamhester.reddit.ui.activities.ContentActivity.VOTABLE_EXTRA;

/** Callbacks called when a link is clicked. */
public class ContentClickCallbacksImpl implements ContentClickCallbacks {
  private final EventBus bus;

  public ContentClickCallbacksImpl(EventBus bus) {
    this.bus = bus;
  }

  @Override
  public void onLinkClicked(final Link link) {
    final CustomTabsIntent customTab = new CustomTabsIntent.Builder().build();
    bus.post(new StartActivityMessage() {
      @Override
      public void startActivity(Context context) {
        customTab.launchUrl(context, link.getUri());
      }
    });
  }

  @Override
  public void navigateTo(Parcelable parcelable) {
    if (!(parcelable instanceof Submission)) {
      return;
    }
    Submission submission = (Submission) parcelable;

    final Bundle args = new Bundle();
    args.putParcelable(VOTABLE_EXTRA, parcelable);
    args.putString(TYPE_EXTRA, COMMENTS);
    args.putString(PERMALINK_EXTRA, submission.getPermalink());

    bus.post(new StartActivityMessage() {
      @Override
      public void startActivity(Context context) {
        final Intent i = new Intent(context, ContentActivity.class);
        i.putExtras(args);
        context.startActivity(i);
      }
    });
  }
}
