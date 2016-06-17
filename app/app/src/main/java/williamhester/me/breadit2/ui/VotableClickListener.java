package williamhester.me.breadit2.ui;

import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.models.Votable;

/**
 * Created by william on 6/17/16.
 */
public interface VotableClickListener {

  void onVotableClicked(Votable votable);

  void onLinkClicked(Link link);

}
