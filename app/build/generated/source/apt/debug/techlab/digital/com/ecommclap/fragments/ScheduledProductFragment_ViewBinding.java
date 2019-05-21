// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ScheduledProductFragment_ViewBinding implements Unbinder {
  private ScheduledProductFragment target;

  private View view2131296344;

  @UiThread
  public ScheduledProductFragment_ViewBinding(final ScheduledProductFragment target, View source) {
    this.target = target;

    View view;
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.no_orders = Utils.findRequiredViewAsType(source, R.id.user_message, "field 'no_orders'", TextView.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'mRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.cancel_schedular_product, "field 'mCancel_schedular_product' and method 'cancel'");
    target.mCancel_schedular_product = Utils.castView(view, R.id.cancel_schedular_product, "field 'mCancel_schedular_product'", Button.class);
    view2131296344 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cancel(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ScheduledProductFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swipeRefreshLayout = null;
    target.no_orders = null;
    target.mRecyclerView = null;
    target.mCancel_schedular_product = null;

    view2131296344.setOnClickListener(null);
    view2131296344 = null;
  }
}
