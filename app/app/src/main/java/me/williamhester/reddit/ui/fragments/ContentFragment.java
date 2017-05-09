package me.williamhester.reddit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import me.williamhester.reddit.R;
import me.williamhester.reddit.ui.VotableCallbacks;
import me.williamhester.reddit.ui.adapters.ContentAdapter;

/** The base fragment for holding a list of items. */
public abstract class ContentFragment<A extends ContentAdapter> extends BaseFragment
    implements VotableCallbacks {

  protected A adapter;

  @BindView(R.id.recycler_view)
  protected RecyclerView recyclerView;

  protected LinearLayoutManager layoutManager;
  private boolean isLoading;

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

  protected boolean isLoading() {
    return isLoading;
  }

  protected void setLoading(boolean loading) {
    this.isLoading = loading;
  }

  protected abstract void loadContent();

  protected abstract A createAdapter(Bundle savedInstanceState);
}
