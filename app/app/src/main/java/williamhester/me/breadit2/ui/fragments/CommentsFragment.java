package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import williamhester.me.breadit2.models.Submission;
import williamhester.me.breadit2.presenters.CommentsPresenter;
import williamhester.me.breadit2.ui.adapters.CommentsAdapter;

/**
 * Created by william on 6/17/16.
 */
public class CommentsFragment extends ContentFragment<CommentsPresenter, CommentsAdapter> {

  private static final String PERMALINK = "permalink";
  private static final String SUBMISSION = "submission";

  public static CommentsFragment newInstance(String permalink, Submission s) {
    Bundle args = new Bundle();
    args.putString(PERMALINK, permalink);
    args.putParcelable(SUBMISSION, s);
    CommentsFragment fragment = new CommentsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    contentPresenter.load(new CommentsPresenter.OnCommentsLoadedListener() {
      @Override
      public void onCommentsLoaded() {
        adapter.setSubmission(contentPresenter.getSubmission());
        adapter.notifyDataSetChanged();
      }
    });
  }

  @Override
  protected CommentsPresenter createPresenter() {
    String permalink = getArguments().getString(PERMALINK);
    Submission submission = getArguments().getParcelable(SUBMISSION);
    return new CommentsPresenter(permalink, submission);
  }

  @Override
  protected CommentsAdapter createAdapter(Bundle savedInstanceState) {
    return new CommentsAdapter(getLayoutInflater(savedInstanceState), clickListener.get(),
        contentPresenter.getSubmission(), contentPresenter.getComments());
  }
}
