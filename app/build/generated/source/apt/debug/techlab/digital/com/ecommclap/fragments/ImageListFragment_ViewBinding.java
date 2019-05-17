// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ImageListFragment_ViewBinding implements Unbinder {
  private ImageListFragment target;

  @UiThread
  public ImageListFragment_ViewBinding(ImageListFragment target, View source) {
    this.target = target;

    target.mEtaContainer = Utils.findRequiredViewAsType(source, R.id.eta_container, "field 'mEtaContainer'", LinearLayout.class);
    target.mCategoryEta = Utils.findRequiredViewAsType(source, R.id.eta_arrival, "field 'mCategoryEta'", TextView.class);
    target.mNoResults = Utils.findRequiredViewAsType(source, R.id.noResults, "field 'mNoResults'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEtaContainer = null;
    target.mCategoryEta = null;
    target.mNoResults = null;
  }
}
