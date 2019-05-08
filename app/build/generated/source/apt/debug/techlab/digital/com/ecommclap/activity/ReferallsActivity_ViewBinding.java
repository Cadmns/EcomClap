// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ReferallsActivity_ViewBinding implements Unbinder {
  private ReferallsActivity target;

  @UiThread
  public ReferallsActivity_ViewBinding(ReferallsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReferallsActivity_ViewBinding(ReferallsActivity target, View source) {
    this.target = target;

    target.mReferralCode = Utils.findRequiredViewAsType(source, R.id.referralCode, "field 'mReferralCode'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReferallsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mReferralCode = null;
  }
}
