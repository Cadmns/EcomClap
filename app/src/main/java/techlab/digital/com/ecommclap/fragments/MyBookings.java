package techlab.digital.com.ecommclap.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.UserConfirmation;
import techlab.digital.com.ecommclap.adapter.OrdersListAdapter;
import techlab.digital.com.ecommclap.model.historyModel.HistoryResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookings extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ImageView error_image; SessionManager sessionManager;
    @BindView(R.id.swipe)
   SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.user_message)
    TextView no_orders;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OrdersListAdapter mAdapter;
    private long mLastClickTime = 0; String main_url;
    TextView message, user_message;
    String filter=""; String tagFlow="";
    View view;
    LinearLayout layoutCartNoItems,layoutCartLoginNeeded;
    RelativeLayout layoutHistoryItems;
    public MyBookings() {
        // Required empty public constructor
    }


    private void setCartLayout(){
        layoutHistoryItems = (RelativeLayout) view.findViewById(R.id.layout_items);
        layoutCartNoItems = (LinearLayout)  view.findViewById(R.id.layout_cart_empty);
        layoutCartLoginNeeded = (LinearLayout)  view.findViewById(R.id.layout_login_empty);

        if (sessionManager.isLoggedIn()) {
            /*request server to fetch the cart items*/

            fetchUsersHistory();
        }
        else {

            layoutCartLoginNeeded.setVisibility(View.VISIBLE);
            layoutHistoryItems.setVisibility(View.GONE);
            layoutCartNoItems.setVisibility(View.GONE);

            Button bStartShopping = (Button)view.findViewById(R.id.btn_login);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sessionManager.checkLogin();
                }
            });

        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the highlight_remove for this fragment
        view= inflater.inflate(R.layout.fragment_my_bookings, container, false);
        ButterKnife.bind(this,view);
        initViews();
        setCartLayout();
        return view;
    }

    private void initViews(){
        sessionManager = new SessionManager(getContext());

        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        /*if (sessionManager.getKeySession()!=null) {
            fetchUsersHistory(sessionManager.getUserId());
        }
        else
        {
            *//*prompt user to login*//*
            Toast.makeText(getContext(),"login needed",Toast.LENGTH_SHORT).show();
        }*/

    }

    public static MyBookings newInstance() {
        MyBookings fragment = new MyBookings();
        return fragment;
    }

    ProgressDialog progressDialog;
    private void fetchUsersHistory(){


        progressDialog = new ProgressDialog(getContext());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonArray> call = apiService.fetchUserHistory("Bearer " + sessionManager.getKeySession(), sessionManager.getUserId());

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    try {
                        JSONArray jsonArray = new JSONArray(String.valueOf(response.body()));
                        if (jsonArray.isNull(0)){
                            no_orders.setVisibility(View.VISIBLE);
                        }
                        else {
                    genratingList(jsonArray);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{
                    no_orders.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
              //  initSnackBar(t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // finish();
            }
        });


    }




    @Override
    public void onRefresh() {
        if (sessionManager.isLoggedIn()) {
            /*request server to fetch the cart items*/

            Log.w("sessionmanager",sessionManager.getKeySession());
            fetchUsersHistory();
        }
        else {

            layoutCartLoginNeeded.setVisibility(View.VISIBLE);
            layoutHistoryItems.setVisibility(View.GONE);
            layoutCartNoItems.setVisibility(View.GONE);

            Button bStartShopping = (Button)view.findViewById(R.id.btn_login);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionManager.isNumberVerified()){
                        sessionManager.checkLogin();
                    }
                    else{
                        Intent intent = new Intent(getContext(),UserConfirmation.class);
                        startActivity(intent);
                    }
                }
            });

        }

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    List<HistoryResponse> mFinalResponse;
    private void genratingList(JSONArray jsonArray){
        ArrayList<HistoryResponse> mFinalList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HistoryResponse>>(){}.getType());


        setAdapter(mFinalList);

    }

    private void setAdapter(ArrayList<HistoryResponse> mFinalList) {

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrdersListAdapter(getContext(), mFinalList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }
}
