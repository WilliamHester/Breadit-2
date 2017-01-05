package williamhester.me.breadit2.inject;

import javax.inject.Singleton;

import dagger.Component;
import williamhester.me.breadit2.ui.fragments.BaseFragment;

/**
 * Created by william on 6/15/16.
 */
@Component(modules = ApiModule.class)
@Singleton
public interface ApiComponent {
  void inject(BaseFragment api);
}
