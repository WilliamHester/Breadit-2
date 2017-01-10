package williamhester.me.breadit2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import williamhester.me.breadit2.models.Link;
import williamhester.me.breadit2.models.Votable;
import williamhester.me.breadit2.presenters.VotablePresenter;
import williamhester.me.breadit2.ui.adapters.VotableAdapter;

/**  */
public abstract class VotableFragment extends ContentFragment<VotablePresenter, VotableAdapter> {

  private boolean canLoad = true;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    contentPresenter.refreshSubmissions(new VotablePresenter.OnRefreshListener() {
      @Override
      public void onRefreshedVotables(boolean isNew) {
        adapter.notifyDataSetChanged();
      }
    });
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView.addOnScrollListener(new InfiniteLoadScrollListener());
  }

  @Override
  protected VotableAdapter createAdapter(Bundle savedInstanceState) {
    return new VotableAdapter(getLayoutInflater(savedInstanceState), htmlParser,
        contentClickCallbacks, this, contentPresenter.getVotables());
  }

  @Override
  public void onVotableClicked(Votable votable) {
    clickListener.get().navigateTo(votable);
  }

  private class InfiniteLoadScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      int position = layoutManager.findLastVisibleItemPosition();
      if (canLoad && position > contentPresenter.getVotables().size() - 5) {
        canLoad = false;
        setLoading(true);
        contentPresenter.loadMoreSubmissions(new VotablePresenter.OnLoadedMoreListener() {
          @Override
          public void onLoadedMoreVotables(int oldCount, int newCount) {
            setLoading(false);
            if (newCount > oldCount) {
              canLoad = true;
              adapter.notifyItemRangeInserted(oldCount, newCount - oldCount);
            } else {
              canLoad = false;
            }
          }
        });
      }
    }
  }
}
