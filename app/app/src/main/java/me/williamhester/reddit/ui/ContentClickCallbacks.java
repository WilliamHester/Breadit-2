package me.williamhester.reddit.ui;

import android.os.Parcelable;

import me.williamhester.reddit.models.Link;

/** Callback methods for content links being clicked */
public interface ContentClickCallbacks {
  /** Navigates to a given link. */
  void onLinkClicked(Link link);

  /** Navigates to a Parcelable (typically a Submission or User). */
  void navigateTo(Parcelable parcelable);
}
