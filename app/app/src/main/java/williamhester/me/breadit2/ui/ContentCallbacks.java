package williamhester.me.breadit2.ui;

import android.os.Parcelable;

import williamhester.me.breadit2.models.Link;

/** Callbacks when content is clicked */
public interface ContentCallbacks {

  void navigateTo(Parcelable parcelable);
}
