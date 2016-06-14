package williamhester.me.breadit2;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by william on 6/13/16.
 */
public abstract class ContentViewHolder<T> extends RecyclerView.ViewHolder {
  public ContentViewHolder(View itemView) {
    super(itemView);
  }

  public abstract void setContent(T item);
}
