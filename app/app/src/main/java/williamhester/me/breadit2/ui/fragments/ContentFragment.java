package williamhester.me.breadit2.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import williamhester.me.breadit2.R;
import williamhester.me.breadit2.apis.RedditApi;
import williamhester.me.breadit2.ui.ContentCallbacks;
import williamhester.me.breadit2.ui.VotableCallbacks;
import williamhester.me.breadit2.ui.adapters.ContentAdapter;

/** The base fragment for holding a list of items. */
public abstract class ContentFragment<P, A extends ContentAdapter> extends BaseFragment
    implements VotableCallbacks {

  protected A adapter;

  @BindView(R.id.recycler_view)
  protected RecyclerView recyclerView;

  protected LinearLayoutManager layoutManager;
  protected P contentPresenter;
  protected boolean loading;

  protected WeakReference<ContentCallbacks> clickListener;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if (context instanceof ContentCallbacks) {
      clickListener = new WeakReference<>((ContentCallbacks) context);
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    contentPresenter = createPresenter(api);
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

  protected abstract P createPresenter(RedditApi api);

  protected abstract A createAdapter(Bundle savedInstanceState);
}
