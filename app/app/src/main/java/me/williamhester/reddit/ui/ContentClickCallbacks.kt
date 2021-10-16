package me.williamhester.reddit.ui

import android.os.Parcelable

import me.williamhester.reddit.models.Link

/** Callback methods for content links being clicked  */
interface ContentClickCallbacks {
  /** Navigates to a given link.  */
  fun onLinkClicked(link: Link)

  /** Navigates to a Parcelable (typically a Submission or User).  */
  fun navigateTo(parcelable: Parcelable)
}
