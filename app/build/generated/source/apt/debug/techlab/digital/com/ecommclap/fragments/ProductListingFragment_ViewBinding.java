// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ProductListingFragment_ViewBinding implements Unbinder {
  private ProductListingFragment target;

  @UiThread
  public ProductListingFragment_ViewBinding(ProductListingFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerview, "field 'recyclerView'", RecyclerView.class);
    target.noResults = Utils.findRequiredViewAsType(source, R.id.noResults, "field 'noResults'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductListingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.noResults = null;
  }
}
