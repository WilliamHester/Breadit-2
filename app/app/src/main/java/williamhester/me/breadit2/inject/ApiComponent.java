package williamhester.me.breadit2.inject;

import dagger.Component;
import williamhester.me.breadit2.presenters.RedditPresenter;

/**
 * Created by william on 6/15/16.
 */
@Component(modules = ApiModule.class)
public interface ApiComponent {
  void inject(RedditPresenter presenter);
}
