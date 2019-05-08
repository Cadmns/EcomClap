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

public class UpdatePaymentDetailsActivity_ViewBinding implements Unbinder {
  private UpdatePaymentDetailsActivity target;

  @UiThread
  public UpdatePaymentDetailsActivity_ViewBinding(UpdatePaymentDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UpdatePaymentDetailsActivity_ViewBinding(UpdatePaymentDetailsActivity target,
      View source) {
    this.target = target;

    target.mHeaderMessage = Utils.findRequiredViewAsType(source, R.id.header_message, "field 'mHeaderMessage'", TextView.class);
    target.mPageSloganMessage = Utils.findRequiredViewAsType(source, R.id.page_slogan, "field 'mPageSloganMessage'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UpdatePaymentDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mHeaderMessage = null;
    target.mPageSloganMessage = null;
  }
}
