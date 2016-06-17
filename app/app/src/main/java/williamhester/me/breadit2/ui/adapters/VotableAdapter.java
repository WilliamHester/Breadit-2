package williamhester.me.breadit2.ui.adapters;

import android.view.LayoutInflater;

import java.util.List;

import williamhester.me.breadit2.models.Votable;

/**
 * Created by william on 6/16/16.
 */
public class VotableAdapter extends ContentAdapter {

  protected List<Votable> votables;

  public VotableAdapter(LayoutInflater inflater, List<Votable> votables) {
    super(inflater);
    this.votables = votables;
  }

  @Override
  public int getItemCount() {
    return votables.size();
  }

  @Override
  protected Object getItemForPosition(int position) {
    return votables.get(position);
  }
}
