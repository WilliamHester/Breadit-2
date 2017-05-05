package me.williamhester.reddit.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import me.williamhester.reddit.BreaditApplication;
import me.williamhester.reddit.events.StartActivityEvent;

public abstract class BaseActivity extends AppCompatActivity {
  @Inject EventBus bus;

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

  @Subscribe
  public void startActivity(StartActivityEvent request) {
    request.startActivity(this);
  }

  protected abstract int getLayoutId();
}
