package me.williamhester.reddit.inject;

import javax.inject.Singleton;

import dagger.Component;
import me.williamhester.reddit.ui.activities.BaseActivity;
import me.williamhester.reddit.ui.fragments.BaseFragment;

/**  */
@Component(modules = {ApiModule.class, ApplicationModule.class})
@Singleton
public interface ApplicationComponent {
  void inject(BaseFragment fragment);
  void inject(BaseActivity activity);
}
