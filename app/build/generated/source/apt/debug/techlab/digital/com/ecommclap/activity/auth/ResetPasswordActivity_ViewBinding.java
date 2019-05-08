// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity.auth;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ResetPasswordActivity_ViewBinding implements Unbinder {
  private ResetPasswordActivity target;

  @UiThread
  public ResetPasswordActivity_ViewBinding(ResetPasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ResetPasswordActivity_ViewBinding(ResetPasswordActivity target, View source) {
    this.target = target;

    target.submit_btn = Utils.findRequiredViewAsType(source, R.id.change_password_btn, "field 'submit_btn'", Button.class);
    target.first_password = Utils.findRequiredViewAsType(source, R.id.input_first_password, "field 'first_password'", EditText.class);
    target.second_password = Utils.findRequiredViewAsType(source, R.id.input_second_password, "field 'second_password'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ResetPasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.submit_btn = null;
    target.first_password = null;
    target.second_password = null;
  }
}
