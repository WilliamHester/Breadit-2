package williamhester.me.breadit2;

import android.app.Application;

import williamhester.me.breadit2.inject.ApiComponent;
import williamhester.me.breadit2.inject.DaggerApiComponent;

/**
 * Created by william on 6/15/16.
 */
public class BreaditApplication extends Application {

  private ApiComponent apiComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    apiComponent = DaggerApiComponent.builder().build();
  }

  public ApiComponent getApiComponent() {
    return apiComponent;
  }
}
