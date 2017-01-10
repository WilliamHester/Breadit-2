package williamhester.me.breadit2.ui;

import android.content.Context;
import android.support.customtabs.CustomTabsIntent;

import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.models.Votable;

/** Callbacks called when a link is clicked. */
public class ContentClickCallbacksImpl implements ContentClickCallbacks {
  private final Context context;

  public ContentClickCallbacksImpl(Context context) {
    this.context = context;
  }

  @Override
  public void onLinkClicked(Link link) {
    CustomTabsIntent customTab = new CustomTabsIntent.Builder().build();
    customTab.launchUrl(context, link.getUri());
  }
}
