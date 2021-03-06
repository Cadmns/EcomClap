// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class MainMenuFragment_ViewBinding implements Unbinder {
  private MainMenuFragment target;

  private View view2131296322;

  private View view2131296472;

  private View view2131296549;

  private View view2131296458;

  private View view2131296451;

  private View view2131296332;

  private View view2131296726;

  private View view2131296783;

  private View view2131296439;

  @UiThread
  public MainMenuFragment_ViewBinding(final MainMenuFragment target, View source) {
    this.target = target;

    View view;
    target.sliderDotsPanel = Utils.findRequiredViewAsType(source, R.id.sliderDots, "field 'sliderDotsPanel'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.beauty_spa, "field 'mBeauty_spa' and method 'beautySpa'");
    target.mBeauty_spa = Utils.castView(view, R.id.beauty_spa, "field 'mBeauty_spa'", CardView.class);
    view2131296322 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.beautySpa(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.home_service, "field 'mHomeService' and method 'homeService'");
    target.mHomeService = Utils.castView(view, R.id.home_service, "field 'mHomeService'", CardView.class);
    view2131296472 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.homeService(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.medical_health, "field 'mMedical_health' and method 'medicalhealth'");
    target.mMedical_health = Utils.castView(view, R.id.medical_health, "field 'mMedical_health'", CardView.class);
    view2131296549 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.medicalhealth(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.fruit_vegetables, "field 'mFruits_vegetables' and method 'fruitVegetables'");
    target.mFruits_vegetables = Utils.castView(view, R.id.fruit_vegetables, "field 'mFruits_vegetables'", CardView.class);
    view2131296458 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fruitVegetables(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.food, "field 'mFood' and method 'food'");
    target.mFood = Utils.castView(view, R.id.food, "field 'mFood'", CardView.class);
    view2131296451 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.food(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.breakfast_needs, "field 'mBreakFastNeeds' and method 'breakfastNeeds'");
    target.mBreakFastNeeds = Utils.castView(view, R.id.breakfast_needs, "field 'mBreakFastNeeds'", CardView.class);
    view2131296332 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.breakfastNeeds(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.sports_goods, "field 'mSportsGoods' and method 'sportsgoods'");
    target.mSportsGoods = Utils.castView(view, R.id.sports_goods, "field 'mSportsGoods'", CardView.class);
    view2131296726 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.sportsgoods(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tutors, "field 'mTutors' and method 'tutors'");
    target.mTutors = Utils.castView(view, R.id.tutors, "field 'mTutors'", CardView.class);
    view2131296783 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.tutors(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.exploreMore, "field 'mExpolre_more' and method 'exporeMore'");
    target.mExpolre_more = Utils.castView(view, R.id.exploreMore, "field 'mExpolre_more'", CardView.class);
    view2131296439 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.exporeMore(p0);
      }
    });
    target.mShimmerViewContainer = Utils.findRequiredViewAsType(source, R.id.shimmer_view_container3, "field 'mShimmerViewContainer'", ShimmerFrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainMenuFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sliderDotsPanel = null;
    target.mBeauty_spa = null;
    target.mHomeService = null;
    target.mMedical_health = null;
    target.mFruits_vegetables = null;
    target.mFood = null;
    target.mBreakFastNeeds = null;
    target.mSportsGoods = null;
    target.mTutors = null;
    target.mExpolre_more = null;
    target.mShimmerViewContainer = null;

    view2131296322.setOnClickListener(null);
    view2131296322 = null;
    view2131296472.setOnClickListener(null);
    view2131296472 = null;
    view2131296549.setOnClickListener(null);
    view2131296549 = null;
    view2131296458.setOnClickListener(null);
    view2131296458 = null;
    view2131296451.setOnClickListener(null);
    view2131296451 = null;
    view2131296332.setOnClickListener(null);
    view2131296332 = null;
    view2131296726.setOnClickListener(null);
    view2131296726 = null;
    view2131296783.setOnClickListener(null);
    view2131296783 = null;
    view2131296439.setOnClickListener(null);
    view2131296439 = null;
  }
}
