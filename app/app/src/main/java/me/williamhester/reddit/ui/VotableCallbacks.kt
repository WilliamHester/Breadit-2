package me.williamhester.reddit.ui

import me.williamhester.reddit.models.Votable

/** Callbacks for when a votable is clicked.  */
interface VotableCallbacks {
  /** Called when a votable is clicked.  */
  fun onVotableClicked(votable: Votable)
}
