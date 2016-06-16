package williamhester.me.breadit2.ui.adapters;

import android.view.LayoutInflater;

import java.util.List;

import williamhester.me.breadit2.models.Votable;

/**
 * Created by william on 6/16/16.
 */
public class CommentsAdapter extends ContentAdapter {
  public CommentsAdapter(List<Votable> votables, LayoutInflater inflater) {
    super(votables, inflater);
  }
}
