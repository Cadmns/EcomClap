// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.dialog;

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

public class UpdateBillingAdd_ViewBinding implements Unbinder {
  private UpdateBillingAdd target;

  @UiThread
  public UpdateBillingAdd_ViewBinding(UpdateBillingAdd target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UpdateBillingAdd_ViewBinding(UpdateBillingAdd target, View source) {
    this.target = target;

    target.update_address = Utils.findRequiredViewAsType(source, R.id.update_address, "field 'update_address'", Button.class);
    target.mfirstname = Utils.findRequiredViewAsType(source, R.id.input_name_first, "field 'mfirstname'", EditText.class);
    target.lastname = Utils.findRequiredViewAsType(source, R.id.input_name_last, "field 'lastname'", EditText.class);
    target.flat_number = Utils.findRequiredViewAsType(source, R.id.flat_no, "field 'flat_number'", EditText.class);
    target.colony = Utils.findRequiredViewAsType(source, R.id.colony, "field 'colony'", EditText.class);
    target.mAddressLine2 = Utils.findRequiredViewAsType(source, R.id.input_address2, "field 'mAddressLine2'", EditText.class);
    target.pincode = Utils.findRequiredViewAsType(source, R.id.input_post_code, "field 'pincode'", EditText.class);
    target.mPhoneNumber = Utils.findRequiredViewAsType(source, R.id.input_phoneNumber, "field 'mPhoneNumber'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UpdateBillingAdd target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.update_address = null;
    target.mfirstname = null;
    target.lastname = null;
    target.flat_number = null;
    target.colony = null;
    target.mAddressLine2 = null;
    target.pincode = null;
    target.mPhoneNumber = null;
  }
}
