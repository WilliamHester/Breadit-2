package me.williamhester.reddit.apis;

/**
 * Created by william on 6/13/16.
 */
public interface DataCallback<T> {

  void onResponse(T data);

}
