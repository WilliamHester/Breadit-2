package williamhester.me.breadit2.ui;

import android.os.Parcelable;

import williamhester.me.breadit2.models.Link;

/** Callback methods for content links being clicked */
public interface ContentClickCallbacks {
  /** Navigates to a given link. */
  void onLinkClicked(Link link);

  /** Navigates to a Parcelable (typically a Submission or User). */
  void naviageTo(Parcelable parcelable);
}
