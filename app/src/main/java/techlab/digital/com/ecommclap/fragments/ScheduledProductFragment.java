package techlab.digital.com.ecommclap.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.NewCategoryActivity;
import techlab.digital.com.ecommclap.activity.UserConfirmation;
import techlab.digital.com.ecommclap.adapter.ScheduledAdapter;
import techlab.digital.com.ecommclap.adapter.ScheduledChildAdapter;
import techlab.digital.com.ecommclap.model.RemoveScheduleOrder;
import techlab.digital.com.ecommclap.model.UpdateSchuledOrderQuantityModel;
import techlab.digital.com.ecommclap.model.historyModel.HistoryResponse;
import techlab.digital.com.ecommclap.model.historyModel.LineItem;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduledProductFragment extends Fragment implements ScheduledChildAdapter.OnInterfaceListener{
    View view; LinearLayout layout_view_details,layoutCartItems,layoutCartPayments,layoutCartNoItems,layoutCartLoginNeeded;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.user_message)
    TextView no_orders;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout layoutHistoryItems;
    SessionManager session;
    ScheduledChildAdapter mAdapter;
    @BindView(R.id.cancel_schedular_product)
    Button mCancel_schedular_product;
    // variable to track event time
    private long mLastClickTime = 0;
    public ScheduledProductFragment() {
        // Required empty public constructor
    }

    String parent_id,selecteddate;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the highlight_remove for this fragment


         parent_id = getArguments().getString("parent_id");
        selecteddate = getArguments().getString("selectedDate");
        view= inflater.inflate(R.layout.fragment_scheduled_product, container, false);
        ButterKnife.bind(this,view);
        session = new SessionManager(getContext());


        fetchScheduledItems(selecteddate,parent_id);

        return  view;
    }

    ProgressDialog progressDialog;
    private void fetchScheduledItems(String selecteddate, String parent_id){
        progressDialog = new ProgressDialog(getContext());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonArray> call = apiService.fetchChildScheduledProducts("Bearer " + session.getKeySession(), session.getUserId(),parent_id,selecteddate);

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
                            mCancel_schedular_product.setVisibility(View.GONE);
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
            public void onFailure(@NotNull Call<JsonArray> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                //  initSnackBar(t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // finish();
            }
        });

    }


    ArrayList<HistoryResponse> mFinalList;
    private void genratingList(JSONArray jsonArray){
        mFinalList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HistoryResponse>>(){}.getType());

        if (mFinalList.get(0).getStatus().equals("cancelled")){
            no_orders.setVisibility(View.VISIBLE);
            mCancel_schedular_product.setVisibility(View.GONE);
        }else
        setAdapter(mFinalList);

    }

    @OnClick(R.id.cancel_schedular_product)
    public void cancel(View view) {

        /*here we are going to cancel the schedule item*/

        if (mCheckInternetWithMultipleClicks())
        cancelChildOrder();

    }

    private void cancelChildOrder(){
        progressDialog = new ProgressDialog(getContext());
        RemoveScheduleOrder removeScheduleOrder = new RemoveScheduleOrder();
        removeScheduleOrder.setStatus("cancelled");
        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.cancelScheduleOrder("Bearer " + session.getKeySession(),removeScheduleOrder,mFinalList.get(0).getId());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){

                    Toast.makeText(getContext(),"remove successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), NewCategoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }else{
                    Log.e("Error","");
                    if (response.code() == 403){



                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }


    private void setAdapter(ArrayList<HistoryResponse> mFinalList) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ScheduledChildAdapter(getContext(), mFinalList.get(0).getLineItems());

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        mAdapter.mCallback = (ScheduledChildAdapter.OnInterfaceListener) this;

    }

    public static ScheduledProductFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);

        ScheduledProductFragment fragment = new ScheduledProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private Boolean mCheckInternetWithMultipleClicks(){

        if (CheckInternet.isNetwork(getContext())) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;
            //  }
        }else {
            //do something, net is not connected

            Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

        }

        return false;

    }


    @Override
    public void onUpdateItemsClick(View view, int position, int quantity) {
        LineItem lineItem =  mFinalList.get(0).getLineItems().get(position);
        try {
            updateQuantity(lineItem, String.valueOf(quantity));
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
        }

    }



    private void updateQuantity(LineItem key, String quantity){

        UpdateSchuledOrderQuantityModel updateSchuledOrderQuantityModel = new UpdateSchuledOrderQuantityModel();
        updateSchuledOrderQuantityModel.setOrder_id(String.valueOf(mFinalList.get(0).getId()));
        updateSchuledOrderQuantityModel.setProduct_id(String.valueOf(key.getId()));
        updateSchuledOrderQuantityModel.setQuantity(quantity);

        progressDialog = new ProgressDialog(getContext());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.updateScheduleQuantity("Bearer " + session.getKeySession(),updateSchuledOrderQuantityModel);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"Item Updated Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("Error","");
                    if (response.code() == 403){


                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });


    }

}
