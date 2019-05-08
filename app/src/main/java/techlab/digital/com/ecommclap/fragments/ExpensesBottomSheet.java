package techlab.digital.com.ecommclap.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;

public class ExpensesBottomSheet extends BottomSheetDialogFragment{

    List<Date> dateList;
    Button done_btn;
    Button send_notification;
    TextView mdates;
    LinearLayout mlinearLayout,calendar_container;
    RelativeLayout done_btn_layout;
    TextView walleteMoney,reward;
    TextView grandTotal,address;

    String shipping_address,mcurrent_wallet_balance,mcurrent_total_reward_point,mPoint_appleid,mtotal_deduction,mtotal_payable_amount;

    public ExpensesBottomSheet(){

    }
    @SuppressLint("ValidFragment")
    public ExpensesBottomSheet(String current_wallet_balance, String current_total_reward_point, String Point_appleid, String total_deduction, String total_payable_amount, String address){
        mcurrent_wallet_balance = current_wallet_balance;
        mcurrent_total_reward_point =current_total_reward_point;
        mPoint_appleid =Point_appleid;
        mtotal_deduction = total_deduction;
        mtotal_payable_amount =total_payable_amount;
        shipping_address = address;

    }
    @SuppressLint("RestrictedApi")
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.checkout_bottom_sheet, null);
        dialog.setContentView(view);
        address = view.findViewById(R.id.shpping_address);
        walleteMoney = view.findViewById(R.id.wallet_ammount_tv);
        reward = view.findViewById(R.id.reward_tv);
        grandTotal = view.findViewById(R.id.grandTotal_tv);

        address.setText(shipping_address);
        walleteMoney.setText(mcurrent_wallet_balance);

        reward.setText("₹"+mtotal_deduction);
        grandTotal.setText(mtotal_payable_amount);


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