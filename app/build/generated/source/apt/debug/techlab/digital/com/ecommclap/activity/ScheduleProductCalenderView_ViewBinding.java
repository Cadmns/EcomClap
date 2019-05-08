// Generated code from Butter Knife. Do not modify!
package techlab.digital.com.ecommclap.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.squareup.timessquare.CalendarPickerView;
import java.lang.IllegalStateException;
import java.lang.Override;
import techlab.digital.com.ecommclap.R;

public class ScheduleProductCalenderView_ViewBinding implements Unbinder {
  private ScheduleProductCalenderView target;

  @UiThread
  public ScheduleProductCalenderView_ViewBinding(ScheduleProductCalenderView target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ScheduleProductCalenderView_ViewBinding(ScheduleProductCalenderView target, View source) {
    this.target = target;

    target.calendar_view = Utils.findRequiredViewAsType(source, R.id.calendar_view, "field 'calendar_view'", CalendarPickerView.class);
    target.btn_show_dates = Utils.findRequiredViewAsType(source, R.id.btn_show_dates, "field 'btn_show_dates'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ScheduleProductCalenderView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.calendar_view = null;
    target.btn_show_dates = null;
  }
}
