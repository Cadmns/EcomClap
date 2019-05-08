// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ProductDescriptionActivity_ViewBinding implements Unbinder {
  private ProductDescriptionActivity target;

  private View view2131296291;

  @UiThread
  public ProductDescriptionActivity_ViewBinding(ProductDescriptionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProductDescriptionActivity_ViewBinding(final ProductDescriptionActivity target,
      View source) {
    this.target = target;

    View view;
    target.mDescription = Utils.findRequiredViewAsType(source, R.id.description, "field 'mDescription'", TextView.class);
    target.mMainTitle = Utils.findRequiredViewAsType(source, R.id.product_title, "field 'mMainTitle'", TextView.class);
    target.mMainSubTitle = Utils.findRequiredViewAsType(source, R.id.product_subTitle, "field 'mMainSubTitle'", TextView.class);
    target.mBookingPrice = Utils.findRequiredViewAsType(source, R.id.serviceCost, "field 'mBookingPrice'", TextView.class);
    target.mBookingDate = Utils.findRequiredViewAsType(source, R.id.booking_dateView, "field 'mBookingDate'", TextView.class);
    target.mVariations = Utils.findRequiredViewAsType(source, R.id.variations, "field 'mVariations'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.addTocart, "field 'mAddToCart' and method 'addToCart'");
    target.mAddToCart = Utils.castView(view, R.id.addTocart, "field 'mAddToCart'", Button.class);
    view2131296291 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addToCart(p0);
      }
    });
    target.mQuantity = Utils.findRequiredViewAsType(source, R.id.quantity1, "field 'mQuantity'", ElegantNumberButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductDescriptionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mDescription = null;
    target.mMainTitle = null;
    target.mMainSubTitle = null;
    target.mBookingPrice = null;
    target.mBookingDate = null;
    target.mVariations = null;
    target.mAddToCart = null;
    target.mQuantity = null;

    view2131296291.setOnClickListener(null);
    view2131296291 = null;
  }
}
