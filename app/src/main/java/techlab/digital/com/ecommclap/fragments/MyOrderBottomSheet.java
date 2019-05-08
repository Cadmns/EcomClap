package techlab.digital.com.ecommclap.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.MyOrderListAdapter;
import techlab.digital.com.ecommclap.model.historyModel.LineItem;

@SuppressLint("ValidFragment")
public class MyOrderBottomSheet extends BottomSheetDialogFragment {

    List<LineItem> lineItem;
    TextView grand_total,product_name,product_quantity;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyOrderListAdapter mAdapter;
    Context context;
    TextView number_of_item;
Button close;
    @SuppressLint("ValidFragment")
    public MyOrderBottomSheet(List<LineItem> lineItems,Context cntx) {
        this.lineItem=lineItems;
        this.context=cntx;

    }

    @SuppressLint("RestrictedApi")
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.order_bottom_sheet, null);
        dialog.setContentView(view);

        mRecyclerView=view.findViewById(R.id.itemRecyleView);
        mRecyclerView.setNestedScrollingEnabled(false);
        close=view.findViewById(R.id.close_button);
        number_of_item=view.findViewById(R.id.number_of_item);
        String numbrer__item= String.valueOf(lineItem.size());
        number_of_item.setText("Total Number of Items "+numbrer__item);
        setAdapter(lineItem);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialog.cancel();
               dialog.dismiss();
            }
        });

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setPeekHeight(getResources().getDimensionPixelSize(R.dimen.bottom_sheet_height));

        }
    }
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    private void setAdapter(List<LineItem> mFinalList) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyOrderListAdapter(getContext(), mFinalList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }





}
