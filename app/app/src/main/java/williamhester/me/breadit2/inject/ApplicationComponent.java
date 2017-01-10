package williamhester.me.breadit2.inject;

import javax.inject.Singleton;

import dagger.Component;
import williamhester.me.breadit2.ui.fragments.BaseFragment;

/**  */
@Component(modules = {ApiModule.class, HtmlModule.class})
@Singleton
public interface ApplicationComponent {
  void inject(BaseFragment api);
}
