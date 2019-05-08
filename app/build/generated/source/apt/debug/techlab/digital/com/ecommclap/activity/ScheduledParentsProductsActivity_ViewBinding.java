// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ScheduledParentsProductsActivity_ViewBinding implements Unbinder {
  private ScheduledParentsProductsActivity target;

  @UiThread
  public ScheduledParentsProductsActivity_ViewBinding(ScheduledParentsProductsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ScheduledParentsProductsActivity_ViewBinding(ScheduledParentsProductsActivity target,
      View source) {
    this.target = target;

    target.titleTextView = Utils.findRequiredViewAsType(source, R.id.toolbarTitle, "field 'titleTextView'", TextView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.no_orders = Utils.findRequiredViewAsType(source, R.id.user_message, "field 'no_orders'", TextView.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ScheduledParentsProductsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleTextView = null;
    target.swipeRefreshLayout = null;
    target.no_orders = null;
    target.mRecyclerView = null;
  }
}
