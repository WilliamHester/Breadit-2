package williamhester.me.breadit2.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.models.Votable;
import williamhester.me.breadit2.ui.viewholders.ContentViewHolder;
import williamhester.me.breadit2.ui.viewholders.SubmissionImageViewHolder;
import williamhester.me.breadit2.ui.viewholders.SubmissionLinkViewHolder;
import williamhester.me.breadit2.ui.viewholders.SubmissionViewHolder;

/**
 * Created by william on 6/13/16.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentViewHolder<?>> {

  private static final int SUBMISSION = 1;
  private static final int SUBMISSION_IMAGE = 2;
  private static final int SUBMISSION_LINK = 3;

  private List<Votable> votables;
  private LayoutInflater layoutInflater;

  public ContentAdapter(List<Votable> votables, LayoutInflater inflater) {
    this.votables = votables;
    layoutInflater = inflater;
  }

  @Override
  public ContentViewHolder<?> onCreateViewHolder(ViewGroup parent, int viewType) {
    View v;
    switch (viewType) {
      case SUBMISSION_LINK:
        v = layoutInflater.inflate(R.layout.row_submission_link, parent, false);
        return new SubmissionLinkViewHolder(v);
      case SUBMISSION_IMAGE:
        v = layoutInflater.inflate(R.layout.row_submission_image, parent, false);
        return new SubmissionImageViewHolder(v);
      case SUBMISSION:
        v = layoutInflater.inflate(R.layout.row_submission, parent, false);
        return new SubmissionViewHolder(v);
      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(ContentViewHolder<?> holder, int position) {
    switch (getItemViewType(position)) {
      case SUBMISSION_IMAGE:
      case SUBMISSION_LINK:
      case SUBMISSION:
        SubmissionViewHolder subViewHolder = (SubmissionViewHolder) holder;
        subViewHolder.setContent((Submission) votables.get(position));
        break;
      default:
        break;
    }
  }

  @Override
  public int getItemCount() {
    return votables.size();
  }

  @Override
  public int getItemViewType(int position) {
    Votable v = votables.get(position);
    if (v instanceof Submission) {
      Submission sub = (Submission) v;
      if (sub.getUrl().endsWith(".png")) {
        return SUBMISSION_IMAGE;
      }
      if (!sub.isSelf()) {
        return SUBMISSION_LINK;
      }
      return SUBMISSION;
    }
    return -1;
  }
}
