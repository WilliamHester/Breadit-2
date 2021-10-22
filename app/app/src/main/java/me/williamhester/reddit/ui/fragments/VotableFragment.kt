package me.williamhester.reddit.ui.fragments

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import me.williamhester.reddit.messages.VotableListMessage
import me.williamhester.reddit.models.Votable
import me.williamhester.reddit.ui.adapters.VotableAdapter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/** A fragment to show [Votable]s.  */
abstract class VotableFragment : ContentFragment<VotableAdapter>() {

  protected val votables = ArrayList<Votable>()
  private var canLoad = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (savedInstanceState == null) {
      loadContent()
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recyclerView.addOnScrollListener(InfiniteLoadScrollListener())
  }

  override fun createAdapter(savedInstanceState: Bundle?): VotableAdapter {
    return VotableAdapter(
        LayoutInflater.from(activity),
        htmlParser,
        contentClickCallbacks,
        this,
        votables)
  }

  override fun onVotableClicked(votable: Votable) {
    contentClickCallbacks.navigateTo(votable)
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun receivedVotableList(message: VotableListMessage) {
    isLoading = false
    canLoad = true
    val oldCount = votables.size
    val newVotables = message.votables
    if (newVotables.isNotEmpty()) {
      votables.addAll(message.votables)
      adapter.notifyItemRangeInserted(oldCount, newVotables.size)
    }
  }

  protected val after: String?
    get() {
      if (votables.size == 0) {
        return null
      }
      return votables[votables.size - 1].name
    }

  private inner class InfiniteLoadScrollListener : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      // There's no way layoutManager can be null here.
      val position = layoutManager!!.findLastVisibleItemPosition()
      if (canLoad && position > votables.size - 5) {
        canLoad = false
        isLoading = true
        loadContent()
      }
    }
  }
}
