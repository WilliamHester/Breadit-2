package williamhester.me.breadit2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william on 6/13/16.
 */
public abstract class VotablePresenter {

  private String mTitle;

  protected final List<Votable> mVotables = new ArrayList<>();

  public abstract void loadMoreSubmissions(OnLoadedMoreListener callback);

  public abstract void refreshSubmissions(OnRefreshListener callback);

  public interface OnLoadedMoreListener {
    void onLoadedMoreVotables(int oldCount, int newCount);
  }

  public interface OnRefreshListener {
    void onRefreshedVotables(boolean isNew);
  }

}
