// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class CheckoutProductActivity_ViewBinding implements Unbinder {
  private CheckoutProductActivity target;

  private View view2131296311;

  private View view2131296518;

  private View view2131296333;

  private View view2131296547;

  private View view2131296338;

  private View view2131296658;

  @UiThread
  public CheckoutProductActivity_ViewBinding(CheckoutProductActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CheckoutProductActivity_ViewBinding(final CheckoutProductActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.apply_promocode, "field 'apply_promocode' and method 'applyPromoCode'");
    target.apply_promocode = Utils.castView(view, R.id.apply_promocode, "field 'apply_promocode'", TextView.class);
    view2131296311 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.applyPromoCode(p0);
      }
    });
    target.mScriollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'mScriollView'", NestedScrollView.class);
    view = Utils.findRequiredView(source, R.id.layout_action1, "field 'mEditLayout' and method 'mEditLayout'");
    target.mEditLayout = Utils.castView(view, R.id.layout_action1, "field 'mEditLayout'", LinearLayout.class);
    view2131296518 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mEditLayout(p0);
      }
    });
    target.userAddress = Utils.findRequiredViewAsType(source, R.id.address, "field 'userAddress'", TextView.class);
    target.userBillingName = Utils.findRequiredViewAsType(source, R.id.name, "field 'userBillingName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnAddExpense, "field 'ExpensesDetailsBtn' and method 'mBtnExpenseClicked'");
    target.ExpensesDetailsBtn = Utils.castView(view, R.id.btnAddExpense, "field 'ExpensesDetailsBtn'", Button.class);
    view2131296333 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mBtnExpenseClicked(p0);
      }
    });
    target.promo_code_is_applied = Utils.findRequiredViewAsType(source, R.id.promo_code_applied, "field 'promo_code_is_applied'", LinearLayout.class);
    target.order_money_layout = Utils.findRequiredViewAsType(source, R.id.balance_container, "field 'order_money_layout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.mcheckBox, "field 'reward_checkbox' and method 'mRewardCheckBoxClicked'");
    target.reward_checkbox = Utils.castView(view, R.id.mcheckBox, "field 'reward_checkbox'", CheckBox.class);
    view2131296547 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mRewardCheckBoxClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_signup, "field 'checkout_btn' and method 'mBookProduct'");
    target.checkout_btn = Utils.castView(view, R.id.btn_signup, "field 'checkout_btn'", Button.class);
    view2131296338 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mBookProduct(p0);
      }
    });
    target.mRewardContainer = Utils.findRequiredViewAsType(source, R.id.reward_continer, "field 'mRewardContainer'", LinearLayout.class);
    target.coupon_succesfully_applied = Utils.findRequiredViewAsType(source, R.id.coupon_apllied_success, "field 'coupon_succesfully_applied'", TextView.class);
    view = Utils.findRequiredView(source, R.id.remove_applied_coupon, "field 'remove_promo_code' and method 'removeAppliedPromoCode'");
    target.remove_promo_code = Utils.castView(view, R.id.remove_applied_coupon, "field 'remove_promo_code'", TextView.class);
    view2131296658 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.removeAppliedPromoCode(p0);
      }
    });
    target.first_date = Utils.findRequiredViewAsType(source, R.id.first_date, "field 'first_date'", TextView.class);
    target.second_date = Utils.findRequiredViewAsType(source, R.id.second_date, "field 'second_date'", TextView.class);
    target.mdates_container = Utils.findRequiredViewAsType(source, R.id.dates_container, "field 'mdates_container'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckoutProductActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.apply_promocode = null;
    target.mScriollView = null;
    target.mEditLayout = null;
    target.userAddress = null;
    target.userBillingName = null;
    target.ExpensesDetailsBtn = null;
    target.promo_code_is_applied = null;
    target.order_money_layout = null;
    target.reward_checkbox = null;
    target.checkout_btn = null;
    target.mRewardContainer = null;
    target.coupon_succesfully_applied = null;
    target.remove_promo_code = null;
    target.first_date = null;
    target.second_date = null;
    target.mdates_container = null;

    view2131296311.setOnClickListener(null);
    view2131296311 = null;
    view2131296518.setOnClickListener(null);
    view2131296518 = null;
    view2131296333.setOnClickListener(null);
    view2131296333 = null;
    view2131296547.setOnClickListener(null);
    view2131296547 = null;
    view2131296338.setOnClickListener(null);
    view2131296338 = null;
    view2131296658.setOnClickListener(null);
    view2131296658 = null;
  }
}
