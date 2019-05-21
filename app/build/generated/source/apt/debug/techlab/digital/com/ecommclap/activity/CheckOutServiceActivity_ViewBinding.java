// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class CheckOutServiceActivity_ViewBinding implements Unbinder {
  private CheckOutServiceActivity target;

  private View view2131296505;

  private View view2131296335;

  @UiThread
  public CheckOutServiceActivity_ViewBinding(CheckOutServiceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CheckOutServiceActivity_ViewBinding(final CheckOutServiceActivity target, View source) {
    this.target = target;

    View view;
    target.apply_promocode = Utils.findRequiredViewAsType(source, R.id.apply_promocode, "field 'apply_promocode'", TextView.class);
    target.mScriollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'mScriollView'", ScrollView.class);
    view = Utils.findRequiredView(source, R.id.layout_action1, "field 'mEditLayout' and method 'mEditLayout'");
    target.mEditLayout = Utils.castView(view, R.id.layout_action1, "field 'mEditLayout'", LinearLayout.class);
    view2131296505 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mEditLayout(p0);
      }
    });
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.image_cartlist, "field 'thumbnail'", ImageView.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.item_Name, "field 'mTitle'", TextView.class);
    target.mPrice = Utils.findRequiredViewAsType(source, R.id.item_price, "field 'mPrice'", TextView.class);
    target.userName = Utils.findRequiredViewAsType(source, R.id.name, "field 'userName'", TextView.class);
    target.userDescription = Utils.findRequiredViewAsType(source, R.id.description, "field 'userDescription'", TextView.class);
    target.userAddress = Utils.findRequiredViewAsType(source, R.id.address, "field 'userAddress'", TextView.class);
    target.ExpensesDetailsBtn = Utils.findRequiredViewAsType(source, R.id.btnAddExpense, "field 'ExpensesDetailsBtn'", Button.class);
    view = Utils.findRequiredView(source, R.id.btn_signup, "field 'checkout_btn' and method 'mBookService'");
    target.checkout_btn = Utils.castView(view, R.id.btn_signup, "field 'checkout_btn'", Button.class);
    view2131296335 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mBookService(p0);
      }
    });
    target.promo_code_is_applied = Utils.findRequiredViewAsType(source, R.id.promo_code_applied, "field 'promo_code_is_applied'", LinearLayout.class);
    target.coupon_succesfully_applied = Utils.findRequiredViewAsType(source, R.id.coupon_apllied_success, "field 'coupon_succesfully_applied'", TextView.class);
    target.remove_promo_code = Utils.findRequiredViewAsType(source, R.id.remove_applied_coupon, "field 'remove_promo_code'", TextView.class);
    target.mRewardContainer = Utils.findRequiredViewAsType(source, R.id.reward_continer, "field 'mRewardContainer'", LinearLayout.class);
    target.select_dates_check_box = Utils.findRequiredViewAsType(source, R.id.select_dates_check_box, "field 'select_dates_check_box'", CheckBox.class);
    target.first_date = Utils.findRequiredViewAsType(source, R.id.first_date, "field 'first_date'", TextView.class);
    target.second_date = Utils.findRequiredViewAsType(source, R.id.second_date, "field 'second_date'", TextView.class);
    target.mdates_container = Utils.findRequiredViewAsType(source, R.id.dates_container, "field 'mdates_container'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckOutServiceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.apply_promocode = null;
    target.mScriollView = null;
    target.mEditLayout = null;
    target.thumbnail = null;
    target.mTitle = null;
    target.mPrice = null;
    target.userName = null;
    target.userDescription = null;
    target.userAddress = null;
    target.ExpensesDetailsBtn = null;
    target.checkout_btn = null;
    target.promo_code_is_applied = null;
    target.coupon_succesfully_applied = null;
    target.remove_promo_code = null;
    target.mRewardContainer = null;
    target.select_dates_check_box = null;
    target.first_date = null;
    target.second_date = null;
    target.mdates_container = null;

    view2131296505.setOnClickListener(null);
    view2131296505 = null;
    view2131296335.setOnClickListener(null);
    view2131296335 = null;
  }
}
