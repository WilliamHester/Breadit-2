package me.williamhester.reddit.events;

import android.content.Context;

/** An interface used for callbacks to start an Activity. */
public abstract class StartActivityEvent {
  public abstract void startActivity(Context context);
}
