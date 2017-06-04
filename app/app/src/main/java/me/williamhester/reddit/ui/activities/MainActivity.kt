package me.williamhester.reddit.ui.activities

import android.support.v4.app.Fragment

import me.williamhester.reddit.R
import me.williamhester.reddit.ui.fragments.SubmissionFragment

/** The main activity  */
class MainActivity : ContentActivity() {

  override fun createContentFragment(): Fragment? {
    return SubmissionFragment.newInstance()
  }

  override val layoutId: Int
    get() = R.layout.activity_main
}
