package williamhester.me.breadit2.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import williamhester.me.breadit2.ui.viewholders.ContentViewHolder;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.ui.viewholders.SubmissionViewHolder;
import williamhester.me.breadit2.models.Votable;
import williamhester.me.breadit2.models.Submission;

import java.util.List;

/**
 * Created by william on 6/13/16.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentViewHolder<?>> {

  private static final int SUBMISSION = 1;

  private List<Votable> mVotables;
  private LayoutInflater mLayoutInflater;

  public ContentAdapter(List<Votable> votables, LayoutInflater inflater) {
    mVotables = votables;
    mLayoutInflater = inflater;
  }

  @Override
  public ContentViewHolder<?> onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = mLayoutInflater.inflate(R.layout.row_submission, parent, false);
    return new SubmissionViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ContentViewHolder<?> holder, int position) {
    switch (getItemViewType(position)) {
      case SUBMISSION:
        SubmissionViewHolder subViewHolder = (SubmissionViewHolder) holder;
        subViewHolder.setContent((Submission) mVotables.get(position));
        break;
      default:
        break;
    }
  }

  @Override
  public int getItemCount() {
    return mVotables.size();
  }

  @Override
  public int getItemViewType(int position) {
    Votable v = mVotables.get(position);
    if (v instanceof Submission) {
      return SUBMISSION;
    }
    return -1;
  }
}
