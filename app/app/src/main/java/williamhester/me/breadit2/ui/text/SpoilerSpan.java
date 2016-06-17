package williamhester.me.breadit2.ui.text;

import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by william on 7/20/14.
 */
public class SpoilerSpan extends ClickableSpan {

  private boolean clicked = false;

  @Override
  public void onClick(View view) {
    TextPaint unpaint = new TextPaint();
    clicked = true;
    updateDrawState(unpaint);
    view.invalidate();
  }

  @Override
  public void updateDrawState(@NonNull TextPaint ds) {
    ds.setUnderlineText(true);
    if (!clicked) {
      ds.bgColor = ds.getColor();
    } else {
      ds.bgColor = 0;
    }
  }
}
