package williamhester.me.breadit2.ui.text;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

import williamhester.me.breadit2.models.Votable;
import williamhester.me.breadit2.ui.VotableCallbacks;

/**
 * A {@link LinkMovementMethod} that propogates the click to a {@link VotableCallbacks} if a link is
 * not clicked.
 */
public class VotableMovementMethod extends LinkMovementMethod {

  private final VotableCallbacks votableCallbacks;
  private Votable votable;

  public VotableMovementMethod(VotableCallbacks votableCallbacks) {
    this.votableCallbacks = votableCallbacks;
  }

  public void setVotable(Votable votable) {
    this.votable = votable;
  }

  @Override
  public boolean onTouchEvent(@NonNull TextView widget, @NonNull Spannable buffer,
                              @NonNull MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (!super.onTouchEvent(widget, buffer, event)) {
        votableCallbacks.onVotableClicked(votable);
        return false;
      } else {
        return true;
      }
    }
    return super.onTouchEvent(widget, buffer, event);
  }
}
