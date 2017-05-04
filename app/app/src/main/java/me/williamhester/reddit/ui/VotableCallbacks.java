package me.williamhester.reddit.ui;

import me.williamhester.reddit.models.Votable;

/** Callbacks for when a votable is clicked. */
public interface VotableCallbacks {
  /** Called when a votable is clicked. */
  void onVotableClicked(Votable votable);
}
