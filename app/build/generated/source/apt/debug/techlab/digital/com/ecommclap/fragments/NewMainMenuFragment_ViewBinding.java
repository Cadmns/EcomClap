// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class NewMainMenuFragment_ViewBinding implements Unbinder {
  private NewMainMenuFragment target;

  @UiThread
  public NewMainMenuFragment_ViewBinding(NewMainMenuFragment target, View source) {
    this.target = target;

    target.mainScreenLayout = Utils.findRequiredViewAsType(source, R.id.main_screen, "field 'mainScreenLayout'", RelativeLayout.class);
    target.internetConnection = Utils.findRequiredViewAsType(source, R.id.internetConnection, "field 'internetConnection'", RelativeLayout.class);
    target.mShimmerViewContainer = Utils.findRequiredViewAsType(source, R.id.mshimmer_view_container, "field 'mShimmerViewContainer'", ShimmerFrameLayout.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.sports_banner = Utils.findRequiredViewAsType(source, R.id.sports_banner, "field 'sports_banner'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewMainMenuFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mainScreenLayout = null;
    target.internetConnection = null;
    target.mShimmerViewContainer = null;
    target.mSwipeRefreshLayout = null;
    target.sports_banner = null;
  }
}
