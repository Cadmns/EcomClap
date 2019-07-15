// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class UserAddress_ViewBinding implements Unbinder {
  private UserAddress target;

  private View view2131296515;

  @UiThread
  public UserAddress_ViewBinding(UserAddress target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserAddress_ViewBinding(final UserAddress target, View source) {
    this.target = target;

    View view;
    target.userName = Utils.findRequiredViewAsType(source, R.id.name, "field 'userName'", TextView.class);
    target.userAddress = Utils.findRequiredViewAsType(source, R.id.address, "field 'userAddress'", TextView.class);
    view = Utils.findRequiredView(source, R.id.layout_action1, "field 'mEditLayout' and method 'mEditLayout'");
    target.mEditLayout = Utils.castView(view, R.id.layout_action1, "field 'mEditLayout'", LinearLayout.class);
    view2131296515 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mEditLayout(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserAddress target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userName = null;
    target.userAddress = null;
    target.mEditLayout = null;

    view2131296515.setOnClickListener(null);
    view2131296515 = null;
  }
}
