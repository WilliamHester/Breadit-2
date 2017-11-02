package me.williamhester.reddit.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import me.williamhester.reddit.inject.ActivityComponent

/**
 * A headless fragment for holding state during an Activity's lifetime.
 *
 * An instance of InstanceFragment should be initialized during the creation of an Activity. That
 * Activity's FragmentManager will hold the InstanceFragment until the Activity's lifecycle is over.
 */
class InstanceFragment : Fragment() {

  lateinit var activityComponent: ActivityComponent

  companion object {
    fun newInstance() : InstanceFragment {
      return InstanceFragment()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d("InstanceFragment", "New scope created")

    retainInstance = true
  }
}