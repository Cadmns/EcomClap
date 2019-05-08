// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class UserProfileActivity_ViewBinding implements Unbinder {
  private UserProfileActivity target;

  @UiThread
  public UserProfileActivity_ViewBinding(UserProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserProfileActivity_ViewBinding(UserProfileActivity target, View source) {
    this.target = target;

    target.mName = Utils.findRequiredViewAsType(source, R.id.customerName, "field 'mName'", TextView.class);
    target.mEmail = Utils.findRequiredViewAsType(source, R.id.email, "field 'mEmail'", TextView.class);
    target.mprofile_container = Utils.findRequiredViewAsType(source, R.id.profile_container, "field 'mprofile_container'", RelativeLayout.class);
    target.mnot_login_yet = Utils.findRequiredViewAsType(source, R.id.not_login_yet, "field 'mnot_login_yet'", LinearLayout.class);
    target.login_here_btn = Utils.findRequiredViewAsType(source, R.id.login_first_btn, "field 'login_here_btn'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mName = null;
    target.mEmail = null;
    target.mprofile_container = null;
    target.mnot_login_yet = null;
    target.login_here_btn = null;
  }
}
