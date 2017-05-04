package me.williamhester.reddit;

import android.app.Application;

import me.williamhester.reddit.inject.ApplicationComponent;
import me.williamhester.reddit.inject.DaggerApplicationComponent;

/** Contains the Dagger component for the application. */
public class BreaditApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    applicationComponent = DaggerApplicationComponent.builder().build();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
