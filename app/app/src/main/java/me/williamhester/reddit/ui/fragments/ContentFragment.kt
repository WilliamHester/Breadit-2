package me.williamhester.reddit.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.BindView
import butterknife.ButterKnife
import me.williamhester.reddit.R
import me.williamhester.reddit.ui.VotableCallbacks
import me.williamhester.reddit.ui.adapters.ContentAdapter

/** The base fragment for holding a list of items.  */
abstract class ContentFragment<A : ContentAdapter> : BaseFragment(), VotableCallbacks {

  @BindView(R.id.recycler_view)
  lateinit var recyclerView: RecyclerView

  protected lateinit var adapter: A
  protected var layoutManager: LinearLayoutManager? = null
  protected var isLoading: Boolean = false

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_list, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    layoutManager = LinearLayoutManager(activity)
    recyclerView.layoutManager = layoutManager
    adapter = createAdapter(savedInstanceState)
    recyclerView.adapter = adapter
  }

  protected abstract fun loadContent()

  protected abstract fun createAdapter(savedInstanceState: Bundle?): A
}
