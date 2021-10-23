package me.williamhester.reddit.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.williamhester.reddit.R
import me.williamhester.reddit.ui.fragments.SubmissionFragment
import me.williamhester.reddit.ui.fragments.SubredditsFragment


/** The main activity  */
class MainActivity : ContentActivity() {

  override val layoutId = R.layout.activity_main

  private var selectedTab: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    selectedTab = savedInstanceState?.getInt(SELECTED_TAB, R.id.home) ?: R.id.home

    super.onCreate(savedInstanceState)

    val bottomAppBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
    bottomAppBar.selectedItemId = selectedTab

    bottomAppBar.setOnItemSelectedListener {
      selectTab(it.itemId)
      return@setOnItemSelectedListener true
    }
  }

  private fun selectTab(id: Int) {
    if (selectedTab == id) return
    selectedTab = id

    val transaction = supportFragmentManager.beginTransaction()

    val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
    if (currentFragment != null) {
      transaction.detach(currentFragment)
    }
    val detachedFragment = supportFragmentManager.findFragmentByTag(TAGS[id])
    if (detachedFragment != null) {
      transaction.attach(detachedFragment)
    } else {
      val newFragment = createFragmentForTab(id)
      transaction.add(R.id.fragment_container, newFragment, TAGS[id])
    }
    transaction.commit()
  }

  private fun createFragmentForTab(id: Int): Fragment {
    return when (id) {
      R.id.subreddit_list -> SubredditsFragment()
      R.id.home -> SubmissionFragment.newInstance()
      else -> throw IllegalStateException("Invalid selected tab ID: $id")
    }
  }

  override fun createContentFragment(): Fragment {
    return createFragmentForTab(selectedTab)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    outState.putInt(SELECTED_TAB, selectedTab)
  }

  companion object {
    private const val SELECTED_TAB = "selectedTab"

    private val TAGS = mapOf(
      R.id.subreddit_list to "Subreddit List",
      R.id.home to "Home",
      R.id.user to "User",
      R.id.search to "Search",
    )
  }
}
