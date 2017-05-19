package me.williamhester.reddit.inject

import javax.inject.Singleton

import dagger.Component
import me.williamhester.reddit.ui.activities.BaseActivity
import me.williamhester.reddit.ui.fragments.BaseFragment

/**   */
@Component(modules = arrayOf(ApiModule::class, ApplicationModule::class))
@Singleton
interface ApplicationComponent {
  fun inject(fragment: BaseFragment)
  fun inject(activity: BaseActivity)
}
