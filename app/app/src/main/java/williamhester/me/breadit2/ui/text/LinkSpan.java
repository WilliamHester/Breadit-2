package williamhester.me.breadit2.ui.text;

import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import williamhester.me.breadit2.models.Link;

/**
 * Created by william on 6/16/16.
 */
public class LinkSpan extends ClickableSpan {

  private static final int ORANGE = 0xfff68026;
  private static final int RED = 0xffb31217;
  private static final int GREEN = 0xff85c025;

  private String link;
  private Link mUrl;

  public LinkSpan(String link) {
    this.link = link;
    mUrl = new Link(this.link);
  }

  @Override
  public void updateDrawState(@NonNull TextPaint ds) {
    super.updateDrawState(ds);
    switch (mUrl.getType()) {
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
//    Bundle args = new Bundle();
//    args.putString("permalink", mUrl.getUrl());
//    Intent i;
//    switch (mUrl.getType()) {
//      case Link.SUBMISSION:
//        args.putString("type", "comments");
//        i = new Intent(view.getContext(), BrowseActivity.class);
//        break;
//      case Link.SUBREDDIT:
//        args.putString("type", "subreddit");
//        i = new Intent(view.getContext(), BrowseActivity.class);
//        i.setAction(Intent.ACTION_VIEW);
//        args.putString("subreddit", mUrl.getLinkId());
//        break;
//      case Link.USER:
//        args.putString("type", "user");
//        i = new Intent(view.getContext(), BrowseActivity.class);
//        break;
//      case Link.MESSAGES:
//        args.putString("type", "messages");
//        args.putString("filterType", mUrl.getLinkId());
//        i = new Intent(view.getContext(), BrowseActivity.class);
//        break;
//      default:
//        i = new Intent(view.getContext(), OverlayContentActivity.class);
//        args.putInt("type", OverlayContentActivity.TYPE_LINK);
//        args.putParcelable("Link", mUrl);
//        break;
//
//    }
//    i.putExtras(args);
//    Bundle anim = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fade_in,
//        R.anim.fade_out).toBundle();
//    view.getContext().startActivity(i, anim);
  }

  protected String getLink() {
    return link;
  }
}

