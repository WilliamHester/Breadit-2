package me.williamhester.reddit.ui.activities

import android.support.v4.app.Fragment
import me.williamhester.reddit.R
import me.williamhester.reddit.ui.fragments.SubmissionFragment


/** The main activity  */
class MainActivity : ContentActivity() {

  override val layoutId: Int
    get() = R.layout.activity_main

  override fun createContentFragment(): Fragment? = SubmissionFragment.newInstance()
}
