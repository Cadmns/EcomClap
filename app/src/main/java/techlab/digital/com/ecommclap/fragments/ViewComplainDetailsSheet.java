package techlab.digital.com.ecommclap.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.utility.Const;

@SuppressLint("ValidFragment")
public class ViewComplainDetailsSheet extends BottomSheetDialogFragment {
    String order_id,order_date,complaint_title,complain_status;
    TextView mid,mdate,mtitle,mstatus;
    Context mtx;
    public ViewComplainDetailsSheet(Context context, String title,String id,String date,String status ){
        this.mtx = context;
        order_id = id;
        order_date = date;
        complaint_title = title;
        complain_status = status;
        Log.e("##cp", String.valueOf(order_id));
        Log.e("##cp", String.valueOf(order_date));
        Log.e("##cp ", String.valueOf(complaint_title));
        Log.e("##cp  ", String.valueOf(complain_status));

    }
    @SuppressLint("RestrictedApi")
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.complain_details_sheet, null);
        dialog.setContentView(view);

        mid = view.findViewById(R.id.morder_id);
        mdate = view.findViewById(R.id.Initiatedate);
        mtitle = view.findViewById(R.id.ticket_title);
        mstatus = view.findViewById(R.id.status);


        mid.setText("Order : "+order_id);
        mdate.setText("Date : "+order_date);
        mtitle.setText(complaint_title);
        mstatus.setText("Status : "+complain_status);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
    }

}
