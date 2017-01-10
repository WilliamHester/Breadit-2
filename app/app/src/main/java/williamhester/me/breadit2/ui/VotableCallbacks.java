package williamhester.me.breadit2.ui;

import williamhester.me.breadit2.models.Votable;

/** Callbacks for when a votable is clicked. */
public interface VotableCallbacks {
  /** Called when a votable is clicked. */
  void onVotableClicked(Votable votable);
}
