package me.williamhester.reddit.ui.adapters

import android.view.LayoutInflater

import me.williamhester.reddit.html.HtmlParser
import me.williamhester.reddit.models.Votable
import me.williamhester.reddit.ui.ContentClickCallbacks
import me.williamhester.reddit.ui.VotableCallbacks

/** An adapter that holds [Votable] content. */
class VotableAdapter(
    inflater: LayoutInflater,
    htmlParser: HtmlParser,
    contentClickCallbacks: ContentClickCallbacks,
    clickListener: VotableCallbacks,
    protected var votables: List<Votable>
) : ContentAdapter(inflater, htmlParser, contentClickCallbacks, clickListener) {

  override fun getItemCount(): Int {
    return votables.size
  }

  override fun getItemForPosition(position: Int): Any {
    return votables[position]
  }
}
