package williamhester.me.breadit2;

import android.app.Application;

import williamhester.me.breadit2.inject.ApiComponent;
import williamhester.me.breadit2.inject.DaggerApiComponent;

/**
 * Created by william on 6/15/16.
 */
public class BreaditApplication extends Application {

  private ApiComponent mApiComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    mApiComponent = DaggerApiComponent.builder().build();
  }

  public ApiComponent getApiComponent() {
    return mApiComponent;
  }
}
