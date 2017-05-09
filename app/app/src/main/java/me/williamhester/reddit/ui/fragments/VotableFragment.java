package me.williamhester.reddit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.williamhester.reddit.messages.VotableListMessage;
import me.williamhester.reddit.models.Votable;
import me.williamhester.reddit.ui.adapters.VotableAdapter;

/** A fragment to show {@link Votable}s. */
public abstract class VotableFragment extends ContentFragment<VotableAdapter> {

  private final List<Votable> votables = new ArrayList<>();
  private boolean canLoad = true;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      loadContent();
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView.addOnScrollListener(new InfiniteLoadScrollListener());
  }

  @Override
  protected VotableAdapter createAdapter(Bundle savedInstanceState) {
    return new VotableAdapter(getLayoutInflater(savedInstanceState), htmlParser,
        contentClickCallbacks, this, getVotables());
  }

  @Override
  public void onVotableClicked(Votable votable) {
    contentClickCallbacks.navigateTo(votable);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void receivedVotableList(VotableListMessage message) {
    setLoading(false);
    canLoad = true;
    int oldCount = getVotables().size();
    List<? extends Votable> newVotables = message.getVotables();
    if (newVotables.size() > 0) {
      getVotables().addAll(message.getVotables());
      adapter.notifyItemRangeInserted(oldCount, newVotables.size());
    }
  }

  protected final List<Votable> getVotables() {
    return votables;
  }

  @Nullable
  protected final String getAfter() {
    if (votables.size() == 0) {
      return null;
    }
    return votables.get(votables.size() - 1).getName();
  }

  private class InfiniteLoadScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      int position = layoutManager.findLastVisibleItemPosition();
      if (canLoad && position > getVotables().size() - 5) {
        canLoad = false;
        setLoading(true);
        loadContent();
      }
    }
  }
}
