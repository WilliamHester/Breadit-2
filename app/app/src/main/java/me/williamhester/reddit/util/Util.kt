package me.williamhester.reddit.util

import android.os.Build
import android.text.Html

/** Unescapes an HTML-encoded string.  */
fun unescapeHtml(s: String?): String {
  val nonNull = emptyIfNull(s)
  if (Build.VERSION.SDK_INT > 23) {
    return Html.fromHtml(nonNull, Html.FROM_HTML_MODE_COMPACT).toString()
  } else {
    @Suppress("DEPRECATION")
    return Html.fromHtml(nonNull).toString()
  }
}

fun emptyIfNull(s: String?): String {
  if (s == null) {
    return ""
  }
  return s
}
