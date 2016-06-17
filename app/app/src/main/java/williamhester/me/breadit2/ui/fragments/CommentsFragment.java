package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import williamhester.me.breadit2.presenters.CommentsPresenter;
import williamhester.me.breadit2.ui.adapters.CommentsAdapter;

/**
 * Created by william on 6/17/16.
 */
public class CommentsFragment extends ContentFragment<CommentsPresenter, CommentsAdapter> {

  public static CommentsFragment newInstance() {
    Bundle args = new Bundle();
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
    return new CommentsPresenter("");
  }

  @Override
  protected CommentsAdapter createAdapter(Bundle savedInstanceState) {
    return new CommentsAdapter(getLayoutInflater(savedInstanceState), null,
        contentPresenter.getComments());
  }
}
