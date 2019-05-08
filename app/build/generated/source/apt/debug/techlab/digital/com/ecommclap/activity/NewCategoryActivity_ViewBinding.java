// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class NewCategoryActivity_ViewBinding implements Unbinder {
  private NewCategoryActivity target;

  @UiThread
  public NewCategoryActivity_ViewBinding(NewCategoryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewCategoryActivity_ViewBinding(NewCategoryActivity target, View source) {
    this.target = target;

    target.toolbar_title = Utils.findRequiredViewAsType(source, R.id.toolbarTitle, "field 'toolbar_title'", TextView.class);
    target.mToolBar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolBar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewCategoryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar_title = null;
    target.mToolBar = null;
  }
}
