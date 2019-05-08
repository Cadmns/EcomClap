// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class CartFragment_ViewBinding implements Unbinder {
  private CartFragment target;

  private View view2131296719;

  @UiThread
  public CartFragment_ViewBinding(final CartFragment target, View source) {
    this.target = target;

    View view;
    target.mCoordinatorLayout = Utils.findRequiredViewAsType(source, R.id.container, "field 'mCoordinatorLayout'", CoordinatorLayout.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerview, "field 'mRecyclerView'", RecyclerView.class);
    target.mToalPayment = Utils.findRequiredViewAsType(source, R.id.text_action_bottom1, "field 'mToalPayment'", TextView.class);
    target.mToalViewPayment = Utils.findRequiredViewAsType(source, R.id.whole_payment_summary, "field 'mToalViewPayment'", TextView.class);
    view = Utils.findRequiredView(source, R.id.text_action_bottom2, "field 'mProceed' and method 'proceddToCheckOut'");
    target.mProceed = Utils.castView(view, R.id.text_action_bottom2, "field 'mProceed'", TextView.class);
    view2131296719 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.proceddToCheckOut(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CartFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mCoordinatorLayout = null;
    target.mRecyclerView = null;
    target.mToalPayment = null;
    target.mToalViewPayment = null;
    target.mProceed = null;

    view2131296719.setOnClickListener(null);
    view2131296719 = null;
  }
}
