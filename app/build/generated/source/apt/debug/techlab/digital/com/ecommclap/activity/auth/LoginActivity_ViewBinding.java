// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity.auth;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131296334;

  private View view2131296537;

  private View view2131296453;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_login, "field 'mLogin' and method 'checkLoginCred'");
    target.mLogin = Utils.castView(view, R.id.btn_login, "field 'mLogin'", Button.class);
    view2131296334 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.checkLoginCred(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.link_signup, "field 'mSignUp' and method 'newSignUp'");
    target.mSignUp = Utils.castView(view, R.id.link_signup, "field 'mSignUp'", TextView.class);
    view2131296537 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.newSignUp(p0);
      }
    });
    target.mUserName = Utils.findRequiredViewAsType(source, R.id.input_email, "field 'mUserName'", EditText.class);
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.input_password, "field 'mPassword'", EditText.class);
    target.mToolBar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolBar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.forgotPassword, "field 'mForgotPassword' and method 'forgotPassword'");
    target.mForgotPassword = Utils.castView(view, R.id.forgotPassword, "field 'mForgotPassword'", TextView.class);
    view2131296453 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.forgotPassword(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLogin = null;
    target.mSignUp = null;
    target.mUserName = null;
    target.mPassword = null;
    target.mToolBar = null;
    target.mForgotPassword = null;

    view2131296334.setOnClickListener(null);
    view2131296334 = null;
    view2131296537.setOnClickListener(null);
    view2131296537 = null;
    view2131296453.setOnClickListener(null);
    view2131296453 = null;
  }
}
