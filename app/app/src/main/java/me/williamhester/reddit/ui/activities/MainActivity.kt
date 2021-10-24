package me.williamhester.reddit.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.common.collect.HashBiMap
import me.williamhester.reddit.R
import me.williamhester.reddit.ui.fragments.SubmissionFragment
import me.williamhester.reddit.ui.fragments.SubredditsFragment


/** The main activity  */
class MainActivity : BaseActivity() {

  override val layoutId = R.layout.activity_main

  private var selectedTab: Int = HOME_POSITION
  private lateinit var viewPager: ViewPager2
  private lateinit var bottomNavigation: BottomNavigationView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    selectedTab = savedInstanceState?.getInt(SELECTED_TAB, HOME_POSITION) ?: HOME_POSITION

    viewPager = findViewById(R.id.view_pager)
    bottomNavigation = findViewById(R.id.bottom_navigation)

    viewPager.adapter = HomeViewPagerAdapter()
    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        bottomNavigation.selectedItemId = POSITION_TO_ID[position]!!
      }
    })
    bottomNavigation.setOnItemSelectedListener {
      selectTab(POSITION_TO_ID.inverse()[it.itemId]!!)
      return@setOnItemSelectedListener true
    }

    bottomNavigation.selectedItemId = selectedTab
    viewPager.currentItem = selectedTab
  }

  private fun selectTab(position: Int) {
    if (selectedTab == position) return
    selectedTab = position

    viewPager.currentItem = position
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    outState.putInt(SELECTED_TAB, selectedTab)
  }

  inner class HomeViewPagerAdapter : FragmentStateAdapter(this) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
      return when (position) {
        SUBREDDIT_LIST_POSITION -> SubredditsFragment()
        HOME_POSITION -> SubmissionFragment.newInstance()
        USER_POSITION -> Fragment() // Placeholder
        SEARCH_POSITION -> Fragment() // Placeholder
        else -> throw IllegalStateException("Unexpected position")
      }
    }
  }

  companion object {
    private const val SELECTED_TAB = "selectedTab"
    private const val SUBREDDIT_LIST_POSITION = 0
    private const val HOME_POSITION = 1
    private const val USER_POSITION = 2
    private const val SEARCH_POSITION = 3

    private val POSITION_TO_ID = HashBiMap.create(
      mutableMapOf(
        SUBREDDIT_LIST_POSITION to R.id.subreddit_list,
        HOME_POSITION to R.id.home,
        USER_POSITION to R.id.user,
        SEARCH_POSITION to R.id.search,
      )
    )
  }
}
