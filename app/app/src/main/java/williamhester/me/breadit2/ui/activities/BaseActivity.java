package williamhester.me.breadit2.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import williamhester.me.breadit2.BreaditApplication;
import williamhester.me.breadit2.events.StartActivityEvent;

public abstract class BaseActivity extends AppCompatActivity {
  @Inject Bus bus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());

    BreaditApplication application = (BreaditApplication) getApplicationContext();
    application.getApplicationComponent().inject(this);
  }

  @Override
  protected void onStart() {
    super.onStart();

    bus.register(this);
  }

  @Override
  protected void onStop() {
    super.onStop();

    bus.unregister(this);
  }

  public abstract void startActivity(StartActivityEvent request);

  protected abstract int getLayoutId();
}
