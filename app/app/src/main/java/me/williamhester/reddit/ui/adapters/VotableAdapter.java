package me.williamhester.reddit.ui.adapters;

import android.view.LayoutInflater;

import java.util.List;

import me.williamhester.reddit.html.HtmlParser;
import me.williamhester.reddit.models.Votable;
import me.williamhester.reddit.ui.ContentClickCallbacks;
import me.williamhester.reddit.ui.VotableCallbacks;

/**
 * Created by william on 6/16/16.
 */
public class VotableAdapter extends ContentAdapter {

  protected List<Votable> votables;

  public VotableAdapter(LayoutInflater inflater,
      HtmlParser htmlParser,
      ContentClickCallbacks contentClickCallbacks,
      VotableCallbacks clickListener,
      List<Votable> votables) {
    super(inflater, htmlParser, contentClickCallbacks, clickListener);
    this.votables = votables;
  }

  @Override
  public int getItemCount() {
    return votables.size();
  }

  @Override
  public Object getItemForPosition(int position) {
    return votables.get(position);
  }
}
