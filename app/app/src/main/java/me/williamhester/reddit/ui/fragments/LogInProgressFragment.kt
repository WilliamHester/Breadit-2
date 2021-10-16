package me.williamhester.reddit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.williamhester.reddit.R

/** Fragment that shows the user that they are currently being logged in. */
class LogInProgressFragment : BaseFragment() {

  companion object {
    fun newInstance() = LogInProgressFragment()
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_log_in_progress, container, false)
  }

}