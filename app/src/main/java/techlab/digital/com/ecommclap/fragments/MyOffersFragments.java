package techlab.digital.com.ecommclap.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.MyCouponAdapter;
import techlab.digital.com.ecommclap.model.couponModel.CouponDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOffersFragments extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    private long mLastClickTime = 0;
    SessionManager sessionManager;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.RecyleViewCoupon)
    RecyclerView mRecyclerView;
    List<CouponDetailsResponse> mList;
    RecyclerView.LayoutManager mLayoutManager;
    MyCouponAdapter mAdapter;
    public MyOffersFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_offers, container, false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }


    private void initViews(){
        sessionManager = new SessionManager(getContext());
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        checkInternetAvailablity();

    }

    public static MyOffersFragments newInstance() {
        return new MyOffersFragments();
    }


    @Override
    public void onRefresh() {

        checkInternetAvailablity();
    }

    public void checkInternetAvailablity(){
        if (CheckInternet.isNetwork(getContext())) {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            fetchAllCoupon();
        }
        else
        {
            //do something, net is not connected
            Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void fetchAllCoupon(){
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CouponDetailsResponse>> call = apiService.fetchAllCoupons();
        call.enqueue(new Callback<List<CouponDetailsResponse>>() {
            @Override
            public void onResponse(Call<List<CouponDetailsResponse>> call, Response<List<CouponDetailsResponse>> response) {
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
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
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                Log.e("onFailureOffers",t.getMessage());

            }
        });

    }

    private void setAdapter(List<CouponDetailsResponse> mFinalList) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyCouponAdapter(mFinalList,getContext(),"all_coupons");
        mRecyclerView.setAdapter(mAdapter);

    }

}
