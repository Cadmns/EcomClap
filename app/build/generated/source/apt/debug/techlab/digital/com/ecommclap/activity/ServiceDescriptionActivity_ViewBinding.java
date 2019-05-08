// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ServiceDescriptionActivity_ViewBinding implements Unbinder {
  private ServiceDescriptionActivity target;

  private View view2131296345;

  private View view2131296333;

  @UiThread
  public ServiceDescriptionActivity_ViewBinding(ServiceDescriptionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ServiceDescriptionActivity_ViewBinding(final ServiceDescriptionActivity target,
      View source) {
    this.target = target;

    View view;
    target.mDescription = Utils.findRequiredViewAsType(source, R.id.description, "field 'mDescription'", TextView.class);
    target.mMainTitle = Utils.findRequiredViewAsType(source, R.id.product_title, "field 'mMainTitle'", TextView.class);
    target.mMainSubTitle = Utils.findRequiredViewAsType(source, R.id.product_subTitle, "field 'mMainSubTitle'", TextView.class);
    target.mBookingPrice = Utils.findRequiredViewAsType(source, R.id.serviceCost, "field 'mBookingPrice'", TextView.class);
    target.mBookingDate = Utils.findRequiredViewAsType(source, R.id.booking_dateView, "field 'mBookingDate'", TextView.class);
    view = Utils.findRequiredView(source, R.id.cardView8, "field 'mBookingDateView' and method 'setServiceDate'");
    target.mBookingDateView = Utils.castView(view, R.id.cardView8, "field 'mBookingDateView'", CardView.class);
    view2131296345 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setServiceDate();
      }
    });
    target.mBookingDateTextView = Utils.findRequiredViewAsType(source, R.id.datePicker, "field 'mBookingDateTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_signup, "field 'mBookService' and method 'mBookService'");
    target.mBookService = Utils.castView(view, R.id.btn_signup, "field 'mBookService'", Button.class);
    view2131296333 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mBookService(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ServiceDescriptionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mDescription = null;
    target.mMainTitle = null;
    target.mMainSubTitle = null;
    target.mBookingPrice = null;
    target.mBookingDate = null;
    target.mBookingDateView = null;
    target.mBookingDateTextView = null;
    target.mBookService = null;

    view2131296345.setOnClickListener(null);
    view2131296345 = null;
    view2131296333.setOnClickListener(null);
    view2131296333 = null;
  }
}
