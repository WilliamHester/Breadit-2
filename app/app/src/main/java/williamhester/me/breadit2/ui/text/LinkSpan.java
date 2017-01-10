package williamhester.me.breadit2.ui.text;

import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.ui.ContentClickCallbacks;
import williamhester.me.breadit2.ui.VotableCallbacks;

/**
 * A {@link ClickableSpan} that shows a {@link Link}.
 * The color is dependent upon the {@link Link}'s type.
 */
public class LinkSpan extends ClickableSpan {

  private static final int ORANGE = 0xfff68026;
  private static final int RED = 0xffb31217;
  private static final int GREEN = 0xff85c025;

  private final Link link;
  private final ContentClickCallbacks callbacks;

  public LinkSpan(Link link, ContentClickCallbacks callbacks) {
    this.link = link;
    this.callbacks = callbacks;
  }

  @Override
  public void updateDrawState(@NonNull TextPaint ds) {
    super.updateDrawState(ds);
    switch (link.getType()) {
      case Link.IMGUR_IMAGE:
      case Link.IMGUR_ALBUM:
      case Link.IMGUR_GALLERY:
      case Link.NORMAL_IMAGE:
      case Link.GFYCAT_LINK:
      case Link.GIF:
      case Link.DIRECT_GFY:
        ds.setColor(GREEN);
        break;
      case Link.YOUTUBE:
        ds.setColor(RED);
        break;
      case Link.MESSAGES:
      case Link.SUBMISSION:
      case Link.SUBREDDIT:
      case Link.USER:
      case Link.REDDIT_LIVE:
        ds.setColor(ORANGE);
        break;
    }
  }

  @Override
  public void onClick(View view) {
    callbacks.onLinkClicked(link);
  }
}

