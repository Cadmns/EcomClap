package techlab.digital.com.ecommclap.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.MyCouponAdapter;
import techlab.digital.com.ecommclap.model.couponModel.CouponDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

@SuppressLint("ValidFragment")
public class LoadMyOffersBottomSheet extends BottomSheetDialogFragment implements   MyCouponAdapter.OnInterfaceListener{
    @BindView(R.id.RecyleViewCoupon)
    RecyclerView mRecyclerView;
    Button close_sheet_btn;
    RecyclerView.LayoutManager mLayoutManager;
    MyCouponAdapter mAdapter;
    Context mtx;
    Dialog mdailog;
    SessionManager sessionManager;
    List<CouponDetailsResponse> mFinalList2;
    public LoadMyOffersBottomSheet(Context context){
        this.mtx = context;
        //loadFragment(MyOffersFragments.newInstance());

        sessionManager =new SessionManager(mtx);
        fetchAllCoupon();
    }

    @SuppressLint("RestrictedApi")
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.my_offers_bottom_sheet, null);
        dialog.setContentView(view);
        mdailog = dialog;
        mRecyclerView =view.findViewById(R.id.RecyleViewCoupon1);
        close_sheet_btn = view.findViewById(R.id.close_button);
        sessionManager =new SessionManager(getContext());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        View parent = (View) view.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        view.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);
        params.height = screenHeight;
        parent.setLayoutParams(params);

        close_sheet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //you can use isShowing() because BottomSheet inherit from Dialog class

            }
        });
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
    ProgressDialog progressDialog;
    public void fetchAllCoupon(){
        /*progressDialog = new ProgressDialog(this.mtx);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CouponDetailsResponse>> call = apiService.fetchUserCoupons("Bearer " + sessionManager.getKeySession());
        call.enqueue(new Callback<List<CouponDetailsResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<CouponDetailsResponse>> call, @NotNull Response<List<CouponDetailsResponse>> response) {
               /* if (progressDialog.isShowing())
                    progressDialog.dismiss();*/
                if(response.isSuccessful()){
                    if(response.body()!=null)
                    {
                        setAdapter(response.body());


                    }
                    else{
                        Toast.makeText(getContext(),"data not found",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"server error",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CouponDetailsResponse>> call, Throwable t) {
                /*if (progressDialog.isShowing())
                    progressDialog.dismiss();*/
                Log.e("onFailureOffers",t.getMessage());

            }
        });

    }



    private void setAdapter(List<CouponDetailsResponse> mFinalList) {
        mFinalList2 = mFinalList;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyCouponAdapter(mFinalList,getContext(),"only_mine");
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.mCallback = (MyCouponAdapter.OnInterfaceListener)this;
    }

    @Override
    public void onAppliedCoupon(View view, int position) {

        mdailog.dismiss();
        CouponDetailsResponse objLineItem =mFinalList2.get(position);


        String coupon_id = String.valueOf(objLineItem.getId());
        String coupon_code = objLineItem.getCode();
        String coupon_amount = objLineItem.getAmount();

        Intent intent = new Intent("custom-message");
        intent.putExtra("id",coupon_id);
        intent.putExtra("code",coupon_code);
        intent.putExtra("amount",coupon_amount);
        LocalBroadcastManager.getInstance(mtx).sendBroadcast(intent);

    }
}
