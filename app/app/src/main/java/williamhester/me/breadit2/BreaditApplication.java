package williamhester.me.breadit2;

import android.app.Application;

import williamhester.me.breadit2.inject.ApplicationComponent;
import williamhester.me.breadit2.inject.DaggerApplicationComponent;

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
