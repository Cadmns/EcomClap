// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class RegistrationFragmentDialog_ViewBinding implements Unbinder {
  private RegistrationFragmentDialog target;

  private View view2131296333;

  private View view2131296310;

  @UiThread
  public RegistrationFragmentDialog_ViewBinding(RegistrationFragmentDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegistrationFragmentDialog_ViewBinding(final RegistrationFragmentDialog target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_signup, "field 'signUp_btn' and method 'submituserDetails'");
    target.signUp_btn = Utils.castView(view, R.id.btn_signup, "field 'signUp_btn'", Button.class);
    view2131296333 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.submituserDetails(p0);
      }
    });
    target.f_name = Utils.findRequiredViewAsType(source, R.id.input_name, "field 'f_name'", EditText.class);
    target.l_name = Utils.findRequiredViewAsType(source, R.id.input_name_last, "field 'l_name'", EditText.class);
    target.mobile_no = Utils.findRequiredViewAsType(source, R.id.input_phoneNumber, "field 'mobile_no'", EditText.class);
    target.email_id = Utils.findRequiredViewAsType(source, R.id.input_email, "field 'email_id'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.input_password, "field 'password'", EditText.class);
    target.confirm_password = Utils.findRequiredViewAsType(source, R.id.input_confirm_password, "field 'confirm_password'", EditText.class);
    target.post_code = Utils.findRequiredViewAsType(source, R.id.input_post_code, "field 'post_code'", EditText.class);
    view = Utils.findRequiredView(source, R.id.apply_referralCode, "field 'mReferralCodeView' and method 'onReferralCodeViewClick'");
    target.mReferralCodeView = Utils.castView(view, R.id.apply_referralCode, "field 'mReferralCodeView'", TextView.class);
    view2131296310 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onReferralCodeViewClick();
      }
    });
    target.flat_number = Utils.findRequiredViewAsType(source, R.id.flat_no, "field 'flat_number'", EditText.class);
    target.colony = Utils.findRequiredViewAsType(source, R.id.colony, "field 'colony'", EditText.class);
    target.mAddressLine2 = Utils.findRequiredViewAsType(source, R.id.input_address2, "field 'mAddressLine2'", EditText.class);
    target.mcode_applied_layout = Utils.findRequiredViewAsType(source, R.id.code_applied_layout, "field 'mcode_applied_layout'", LinearLayout.class);
    target.reset_referal = Utils.findRequiredViewAsType(source, R.id.reset_referal, "field 'reset_referal'", TextView.class);
    target.applied_referal_textview = Utils.findRequiredViewAsType(source, R.id.referal_applied_succesfully, "field 'applied_referal_textview'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegistrationFragmentDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.signUp_btn = null;
    target.f_name = null;
    target.l_name = null;
    target.mobile_no = null;
    target.email_id = null;
    target.password = null;
    target.confirm_password = null;
    target.post_code = null;
    target.mReferralCodeView = null;
    target.flat_number = null;
    target.colony = null;
    target.mAddressLine2 = null;
    target.mcode_applied_layout = null;
    target.reset_referal = null;
    target.applied_referal_textview = null;

    view2131296333.setOnClickListener(null);
    view2131296333 = null;
    view2131296310.setOnClickListener(null);
    view2131296310 = null;
  }
}
