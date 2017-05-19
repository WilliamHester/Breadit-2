package me.williamhester.reddit.apis

/**
 * Created by william on 6/13/16.
 */
interface DataCallback<T> {
  fun onResponse(data: T)
}
