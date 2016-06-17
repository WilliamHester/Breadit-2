package williamhester.me.breadit2.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import williamhester.me.breadit2.R;
import williamhester.me.breadit2.ui.fragments.CommentsFragment;

public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    if (f == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, CommentsFragment.newInstance())
          .commit();
    }
  }
}
