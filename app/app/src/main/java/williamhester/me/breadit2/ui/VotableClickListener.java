package williamhester.me.breadit2.ui;

import williamhester.me.breadit2.models.Link;

/**
 * Created by william on 6/17/16.
 */
public interface VotableClickListener {

  void onVotableClicked(int position);

  void onLinkClicked(Link link);

}
