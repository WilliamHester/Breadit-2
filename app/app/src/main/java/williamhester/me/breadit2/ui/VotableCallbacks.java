package williamhester.me.breadit2.ui;

import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.models.Votable;

/**
 * Created by william on 6/17/16.
 */
public interface VotableCallbacks {
  /** Called when a votable is clicked. */
  void onVotableClicked(Votable votable);

  /** Called when a link in a votable is clicked. */
  void onLinkClicked(Link link);
}
