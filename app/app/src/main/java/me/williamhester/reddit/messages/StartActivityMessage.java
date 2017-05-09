package me.williamhester.reddit.messages;

import android.content.Context;

/** An interface used for callbacks to start an Activity. */
public abstract class StartActivityMessage {
  public abstract void startActivity(Context context);
}
