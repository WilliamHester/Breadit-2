package me.williamhester.reddit

import android.app.Application
import io.realm.Realm

import me.williamhester.reddit.inject.ApplicationComponent
import me.williamhester.reddit.inject.ApplicationModule
import me.williamhester.reddit.inject.DaggerApplicationComponent

/** Contains the Dagger component for the application.  */
class BreaditApplication : Application() {

  lateinit var applicationComponent: ApplicationComponent
    private set

  override fun onCreate() {
    super.onCreate()

    Realm.init(this)
    applicationComponent =
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
  }
}
