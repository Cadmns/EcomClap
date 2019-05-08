// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

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

public class ProductListings_ViewBinding implements Unbinder {
  private ProductListings target;

  @UiThread
  public ProductListings_ViewBinding(ProductListings target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProductListings_ViewBinding(ProductListings target, View source) {
    this.target = target;

    target.mToolBarName = Utils.findRequiredViewAsType(source, R.id.toolbarTitle, "field 'mToolBarName'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerview, "field 'recyclerView'", RecyclerView.class);
    target.noResults = Utils.findRequiredViewAsType(source, R.id.noResults, "field 'noResults'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductListings target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolBarName = null;
    target.recyclerView = null;
    target.noResults = null;
  }
}
