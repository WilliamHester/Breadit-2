package me.williamhester.reddit.ui.fragments

import android.os.Bundle

/** A fragment that shows Submissions  */
class SubmissionFragment : VotableFragment() {

  override fun loadContent() {
    redditClient.getSubmissions("", null, after)
  }

  companion object {
    fun newInstance(): SubmissionFragment {
      val args = Bundle()
      val fragment = SubmissionFragment()
      fragment.arguments = args
      return fragment
    }
  }
}
