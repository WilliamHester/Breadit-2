package williamhester.me.breadit2.inject;

import javax.inject.Singleton;

import dagger.Component;
import williamhester.me.breadit2.ui.activities.BaseActivity;
import williamhester.me.breadit2.ui.fragments.BaseFragment;

/**  */
@Component(modules = {ApiModule.class, ApplicationModule.class})
@Singleton
public interface ApplicationComponent {
  void inject(BaseFragment fragment);
  void inject(BaseActivity activity);
}
