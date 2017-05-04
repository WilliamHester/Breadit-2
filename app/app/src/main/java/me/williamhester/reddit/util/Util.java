package me.williamhester.reddit.util;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;

/** Utility functions */
public class Util {
  private Util () {}

  /** Unescapes an HTML-encoded string. */
  @NonNull
  public static String unescapeHtml(String s) {
    s = emptyIfNull(s);
    if (Build.VERSION.SDK_INT > 23) {
      return Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT).toString();
    } else {
      return Html.fromHtml(s).toString();
    }
  }

  @NonNull
  public static String emptyIfNull(String s) {
    if (s == null) {
      return "";
    }
    return s;
  }
}
