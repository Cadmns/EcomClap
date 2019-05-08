package techlab.digital.com.ecommclap.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import techlab.digital.com.ecommclap.adapter.ScheduledAdapter;
import techlab.digital.com.ecommclap.model.historyModel.HistoryResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ScheduledParentsProductsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ScheduledAdapter.OnInterfaceListener {
    @BindView(R.id.toolbarTitle)
    TextView titleTextView;
    SessionManager sessionManager;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.user_message)
    TextView no_orders;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    LinearLayout layoutCartNoItems,layoutCartLoginNeeded;
    RelativeLayout layoutHistoryItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_products);
        ButterKnife.bind(this);

        setToolBar();
        initViews();
        setCartLayout();

    }

    private void initViews(){
        sessionManager = new SessionManager(getApplicationContext());
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.title_scheduled_products));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onRefresh() {
        if (sessionManager.isLoggedIn()) {
            /*request server to fetch the cart items*/
            fetchUsersScheduledProducts();
        }
        else {

            layoutCartLoginNeeded.setVisibility(View.VISIBLE);
            layoutHistoryItems.setVisibility(View.GONE);
            layoutCartNoItems.setVisibility(View.GONE);

            Button bStartShopping = (Button)findViewById(R.id.btn_login);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sessionManager.isNumberVerified()){
                        sessionManager.checkLogin();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),UserConfirmation.class);
                        startActivity(intent);
                    }
                }
            });

        }

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }


    private void setCartLayout(){
        layoutHistoryItems = (RelativeLayout) findViewById(R.id.layout_items);
        layoutCartNoItems = (LinearLayout)  findViewById(R.id.layout_cart_empty);
        layoutCartLoginNeeded = (LinearLayout) findViewById(R.id.layout_login_empty);

        if (sessionManager.isLoggedIn()) {
            /*request server to fetch the cart items*/
            fetchUsersScheduledProducts();
        }
        else {

            layoutCartLoginNeeded.setVisibility(View.VISIBLE);
            layoutHistoryItems.setVisibility(View.GONE);
            layoutCartNoItems.setVisibility(View.GONE);

            Button bStartShopping = (Button)findViewById(R.id.btn_login);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sessionManager.checkLogin();
                }
            });

        }

    }

    ProgressDialog progressDialog;
    private void fetchUsersScheduledProducts(){


        progressDialog = new ProgressDialog(ScheduledParentsProductsActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonArray> call = apiService.fetchScheduledProductsparents("Bearer " + sessionManager.getKeySession(), sessionManager.getUserId());

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

    ArrayList<HistoryResponse> mFinalList;
    private void genratingList(JSONArray jsonArray){
        mFinalList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HistoryResponse>>(){}.getType());

        setAdapter(mFinalList);

    }

    ScheduledAdapter mAdapter;
    private void setAdapter(ArrayList<HistoryResponse> mFinalList) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ScheduledAdapter(getApplicationContext(), mFinalList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        mAdapter.mCallback = (ScheduledAdapter.OnInterfaceListener) this;

    }

    String startScheduledate,endScheduledate;
    String mOrderScheduleType;

    @Override
    public void onItemsClick(View view, int position) {
        HistoryResponse response = mFinalList.get(position);


        if (response.getMetaData().size() <=0){

        }
        else {
            /*fetch the date and pass to the activity */
            try {
                if (response.getMetaData().get(1).getKey().equals("start_date") && response.getMetaData().get(2).getKey().equals("end_date") && response.getMetaData().get(3).getKey().equals("order_type")) {
                    startScheduledate = response.getMetaData().get(1).getValue();
                    endScheduledate = response.getMetaData().get(2).getValue();
                    mOrderScheduleType = response.getMetaData().get(3).getValue();
                } else {

                    Toast.makeText(getApplicationContext(), "Error parsing", Toast.LENGTH_SHORT).show();

                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            Intent intent = new Intent(getApplicationContext(), ScheduleProductCalenderView.class);

            bundle.putString("start_date", startScheduledate);
            bundle.putString("end_date", endScheduledate);
            bundle.putString("order_type", mOrderScheduleType);
            bundle.putString("parent_id", String.valueOf(response.getId()));
            intent.putExtras(bundle);

            startActivity(intent);

        }

    }
}
