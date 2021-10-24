package me.williamhester.reddit.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.williamhester.reddit.R
import me.williamhester.reddit.models.Subreddit
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SubredditsFragment : BaseFragment() {

  private lateinit var recyclerView: RecyclerView
  private val subreddits = mutableListOf<Subreddit>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    redditClient.getMySubreddits()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_list, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recyclerView = view.findViewById(R.id.recycler_view)
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = SubredditListAdapter()
  }

  @SuppressLint("NotifyDataSetChanged") // TODO: Update the data set in a granular fashion
  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onSubredditsLoaded(list: List<Subreddit>) {
    subreddits.clear()
    subreddits.addAll(list)

    recyclerView.adapter?.notifyDataSetChanged()
  }

  private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val subredditName = view.findViewById<TextView>(R.id.subreddit_name)

    fun bind(subreddit: Subreddit) {
      subredditName.text = subreddit.displayName
    }
  }

  private inner class SubredditListAdapter : RecyclerView.Adapter<ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = layoutInflater.inflate(R.layout.row_subreddit, parent, false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(subreddits[position])
    }

    override fun getItemCount(): Int {
      return subreddits.size
    }
  }
}