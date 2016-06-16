package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.ui.fragments.BaseFragment;

/**
 * Created by william on 6/12/16.
 */
public abstract class ContentFragment extends BaseFragment {

  @BindView(R.id.recycler_view)
  protected RecyclerView mRecyclerView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_list, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  }

}
