package me.williamhester.reddit.inject;

import dagger.Component;
import me.williamhester.reddit.ui.activities.BaseActivity;
import me.williamhester.reddit.ui.fragments.BaseFragment;

/** An Activity-based scope component, to be used once for each Activity lifecycle */
@Component(
    dependencies = ApplicationComponent.class,
    modules = { ApiModule.class, ActivityModule.class })
@ActivityScope
public interface ActivityComponent {
  void inject(BaseFragment fragment);
  void inject(BaseActivity activity);
}
