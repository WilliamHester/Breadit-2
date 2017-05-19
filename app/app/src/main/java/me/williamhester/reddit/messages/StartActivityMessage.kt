package me.williamhester.reddit.messages

import android.content.Context

/** An interface used for callbacks to start an Activity.  */
abstract class StartActivityMessage {
  abstract fun startActivity(context: Context)
}
