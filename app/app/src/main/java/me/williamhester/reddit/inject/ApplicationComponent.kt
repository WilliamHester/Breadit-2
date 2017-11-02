package me.williamhester.reddit.inject

import dagger.Component
import javax.inject.Singleton

/** The Dagger component for injecting the ApiModule and ApplicationModules. */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
  /** Factory method for creating the ActivityComponent. */
  fun plus(activityModule: ActivityModule) : ActivityComponent
}
