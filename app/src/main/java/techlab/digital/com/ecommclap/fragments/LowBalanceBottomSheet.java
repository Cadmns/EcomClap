package techlab.digital.com.ecommclap.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.wallet_activity.AddMoneyToWallet;

@SuppressLint("ValidFragment")
public class LowBalanceBottomSheet extends BottomSheetDialogFragment {
    TextView net_payable_amount;
    double low_wallet_money;
    Button add_money;
    Double total_wallet_balance;

    @SuppressLint("ValidFragment")
    public LowBalanceBottomSheet(double v, double current_wallet_balance) {
        low_wallet_money = v;
        total_wallet_balance = current_wallet_balance;
    }

    @SuppressLint("RestrictedApi")
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout._lowbalance_bottom_sheet, null);
        dialog.setContentView(view);
        net_payable_amount = view.findViewById(R.id.net_payable_tv);
        add_money = view.findViewById(R.id.add_bal);
        net_payable_amount.setText("" + low_wallet_money);


        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Intent intent = new Intent(getContext(), AddMoneyToWallet.class);
                Bundle bundle = new Bundle();
                bundle.putString("wallet_money", String.valueOf(total_wallet_balance));
                bundle.putInt("flow_flag",-1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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