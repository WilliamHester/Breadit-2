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
import williamhester.me.breadit2.presenters.VotablePresenter;
import williamhester.me.breadit2.ui.adapters.ContentAdapter;

/**
 * Created by william on 6/12/16.
 */
public abstract class ContentFragment extends BaseFragment {

  protected ContentAdapter adapter;

  @BindView(R.id.recycler_view)
  protected RecyclerView recyclerView;

  protected LinearLayoutManager layoutManager;
  protected VotablePresenter contentPresenter;
  protected boolean loading;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    contentPresenter = createPresenter();

    contentPresenter.refreshSubmissions(new VotablePresenter.OnRefreshListener() {
      @Override
      public void onRefreshedVotables(boolean isNew) {
        adapter.notifyDataSetChanged();
      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_list, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    adapter = createAdapter(savedInstanceState);
    recyclerView.setAdapter(adapter);
  }

  protected void setLoading(boolean loading) {
    this.loading = loading;
  }

  protected abstract VotablePresenter createPresenter();

  protected abstract ContentAdapter createAdapter(Bundle savedInstanceState);

}
