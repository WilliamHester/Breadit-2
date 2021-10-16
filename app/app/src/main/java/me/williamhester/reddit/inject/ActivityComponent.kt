package me.williamhester.reddit.inject

import dagger.Subcomponent
import me.williamhester.reddit.ui.activities.BaseActivity
import me.williamhester.reddit.ui.fragments.BaseFragment

/**
 * A Subcomponent of [ApplicationComponent] that should be instantiated each time an Activity is
 * started for the first time.
 */
@Subcomponent(modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent {
  fun inject(fragment: BaseFragment)
  fun inject(activity: BaseActivity)
}
