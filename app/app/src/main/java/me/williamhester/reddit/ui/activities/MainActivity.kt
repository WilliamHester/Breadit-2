package me.williamhester.reddit.ui.activities

import androidx.fragment.app.Fragment
import me.williamhester.reddit.R
import me.williamhester.reddit.ui.fragments.SubmissionFragment


/** The main activity  */
class MainActivity : ContentActivity() {

  override val layoutId = R.layout.activity_main

  override fun createContentFragment(): Fragment = SubmissionFragment.newInstance()
}
