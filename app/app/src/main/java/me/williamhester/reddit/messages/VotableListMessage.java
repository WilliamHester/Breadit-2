package me.williamhester.reddit.messages;

import java.util.List;

import me.williamhester.reddit.models.Votable;

/**
 * Created by williamhester on 5/8/17.
 */
public class VotableListMessage {
  private final List<? extends Votable> votables;

  public VotableListMessage(List<? extends Votable> votableList) {
    this.votables = votableList;
  }

  public List<? extends Votable> getVotables() {
    return votables;
  }
}
