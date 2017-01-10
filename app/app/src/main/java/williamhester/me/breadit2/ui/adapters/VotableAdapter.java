package williamhester.me.breadit2.ui.adapters;

import android.view.LayoutInflater;

import java.util.List;

import williamhester.me.breadit2.html.HtmlParser;
import williamhester.me.breadit2.models.Votable;
import williamhester.me.breadit2.ui.ContentClickCallbacks;
import williamhester.me.breadit2.ui.VotableCallbacks;

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
