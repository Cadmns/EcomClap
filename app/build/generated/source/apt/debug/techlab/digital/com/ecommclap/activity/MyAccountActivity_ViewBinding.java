// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class MyAccountActivity_ViewBinding implements Unbinder {
  private MyAccountActivity target;

  @UiThread
  public MyAccountActivity_ViewBinding(MyAccountActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyAccountActivity_ViewBinding(MyAccountActivity target, View source) {
    this.target = target;

    target.profile_username = Utils.findRequiredViewAsType(source, R.id.profile_name, "field 'profile_username'", TextView.class);
    target.profile_useremail = Utils.findRequiredViewAsType(source, R.id.profile_email, "field 'profile_useremail'", TextView.class);
    target.profile_usernumber = Utils.findRequiredViewAsType(source, R.id.profile_number, "field 'profile_usernumber'", TextView.class);
    target.mfirstname = Utils.findRequiredViewAsType(source, R.id.input_name_first, "field 'mfirstname'", EditText.class);
    target.lastname = Utils.findRequiredViewAsType(source, R.id.input_name_last, "field 'lastname'", EditText.class);
    target.flat_number = Utils.findRequiredViewAsType(source, R.id.flat_no, "field 'flat_number'", EditText.class);
    target.colony = Utils.findRequiredViewAsType(source, R.id.colony, "field 'colony'", EditText.class);
    target.mAddressLine2 = Utils.findRequiredViewAsType(source, R.id.input_address2, "field 'mAddressLine2'", EditText.class);
    target.pincode = Utils.findRequiredViewAsType(source, R.id.input_post_code, "field 'pincode'", EditText.class);
    target.mlayout_container = Utils.findRequiredViewAsType(source, R.id.myaccount_layout_container, "field 'mlayout_container'", RelativeLayout.class);
    target.mPhonenNumber = Utils.findRequiredViewAsType(source, R.id.input_phoneNumber, "field 'mPhonenNumber'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyAccountActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profile_username = null;
    target.profile_useremail = null;
    target.profile_usernumber = null;
    target.mfirstname = null;
    target.lastname = null;
    target.flat_number = null;
    target.colony = null;
    target.mAddressLine2 = null;
    target.pincode = null;
    target.mlayout_container = null;
    target.mPhonenNumber = null;
  }
}
