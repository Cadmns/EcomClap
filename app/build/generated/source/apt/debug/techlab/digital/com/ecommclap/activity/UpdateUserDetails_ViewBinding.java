// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class UpdateUserDetails_ViewBinding implements Unbinder {
  private UpdateUserDetails target;

  private View view2131296337;

  @UiThread
  public UpdateUserDetails_ViewBinding(UpdateUserDetails target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UpdateUserDetails_ViewBinding(final UpdateUserDetails target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_signup, "field 'signUp_btn' and method 'submituserDetails'");
    target.signUp_btn = Utils.castView(view, R.id.btn_signup, "field 'signUp_btn'", Button.class);
    view2131296337 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.submituserDetails(p0);
      }
    });
    target.mfirstname = Utils.findRequiredViewAsType(source, R.id.input_name, "field 'mfirstname'", EditText.class);
    target.lastname = Utils.findRequiredViewAsType(source, R.id.input_name_last, "field 'lastname'", EditText.class);
    target.phone = Utils.findRequiredViewAsType(source, R.id.input_phoneNumber, "field 'phone'", EditText.class);
    target.pincode = Utils.findRequiredViewAsType(source, R.id.input_post_code, "field 'pincode'", EditText.class);
    target.mAddressLine2 = Utils.findRequiredViewAsType(source, R.id.input_address2, "field 'mAddressLine2'", EditText.class);
    target.flat_number = Utils.findRequiredViewAsType(source, R.id.flat_no, "field 'flat_number'", EditText.class);
    target.colony = Utils.findRequiredViewAsType(source, R.id.colony, "field 'colony'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UpdateUserDetails target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.signUp_btn = null;
    target.mfirstname = null;
    target.lastname = null;
    target.phone = null;
    target.pincode = null;
    target.mAddressLine2 = null;
    target.flat_number = null;
    target.colony = null;

    view2131296337.setOnClickListener(null);
    view2131296337 = null;
  }
}
