package me.williamhester.reddit.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.williamhester.reddit.html.HtmlParser;

/**
 * Created by william on 6/13/16.
 */
public abstract class ContentViewHolder<T> extends RecyclerView.ViewHolder {

  public ContentViewHolder(View itemView) {
    super(itemView);
  }

  public abstract void setContent(T item);

  protected String calculateShortTime(int timeInSeconds) {
    int currentTime = (int) (System.currentTimeMillis() / 1000);
    int diff = Math.max(currentTime - timeInSeconds, 0);
    if (diff < 60) {
      return diff + "s";
    }
    if (diff < 3600) {
      return diff / 60 + "m";
    }
    if (diff < 3600 * 24) {
      return diff / 3600 + "h";
    }
    if (diff < 3600 * 24 * 30) {
      return diff / (3600 * 24) + "d";
    }
    if (diff < 3600 * 24 * 30 * 12) {
      return diff / (3600 * 24 * 30) + "mo";
    }
    return diff / (3600 * 24 * 30 * 12) + "y";
  }
}
