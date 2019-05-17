package techlab.digital.com.ecommclap.activity.schedule_products;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.markushi.ui.CircleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.SchedulableItemsAdapter;
import techlab.digital.com.ecommclap.model.VirtualCartSingleton;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponsetTwo;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class SchedulableItemsActivity extends AppCompatActivity implements SchedulableItemsAdapter.OnInterfacListener {
    SessionManager sessionManager;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CircleButton virtualCart;
    LinearLayout checkoutlayout;
    List<ProductListingsModeResponsetTwo> all_data_list=new ArrayList<>();
    final List<ProductListingsModeResponsetTwo> mFinalList_for_cart = new ArrayList<ProductListingsModeResponsetTwo>();
    private long mLastClickTime = 0;
    SchedulableItemsAdapter mAdapter;
    int product_id;
    String category_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedylable_items);
        sessionManager = new SessionManager(getApplicationContext());
        checkoutlayout=findViewById(R.id.checkout_layout);
        virtualCart = findViewById(R.id.vertual_cart);
        setToolBar();
        Intent intent = getIntent();



        if (null != intent.getExtras()) {
            product_id =getIntent().getExtras().getInt("id");
            category_name =getIntent().getExtras().getString("category_name");


            if (CheckInternet.isNetwork(getApplicationContext())) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Log.e("is   name",category_name);
                fetchproducts(category_name);
            }else {
                //do something, net is not connected
                Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }








        mRecyclerView = findViewById(R.id.schedule_recyclerView);
       // fetch_schedulable_Items();
        virtualCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckInternetWithMultipleClicks()) {
                    Intent intent = new Intent(getApplicationContext(), ScheduledCartActivity.class);
                    startActivity(intent);
                }
            }
        });

    }







    ProgressDialog progressDialog;
    private void fetchproducts(String str){


        progressDialog = new ProgressDialog(SchedulableItemsActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);

        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductListingsModeResponsetTwo>> call = apiService.get_product_for_schedule(str);

        call.enqueue(new Callback<List<ProductListingsModeResponsetTwo>>() {
            @Override
            public void onResponse(Call<List<ProductListingsModeResponsetTwo>> call, Response<List<ProductListingsModeResponsetTwo>> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){

                    setAdapter(response.body());
                }else{
                    Log.e("Error","");

                }
            }

            @Override
            public void onFailure(Call<List<ProductListingsModeResponsetTwo>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });

    }














    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
















    public void fetch_schedulable_Items(){
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(this);
            // Setting up message in Progress dialog.
            progressDialog.setMessage("loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ProductListingsModeResponsetTwo>> call = apiService.get_schedulable_products("Bearer " + sessionManager.getKeySession());
            call.enqueue(new Callback<List<ProductListingsModeResponsetTwo>>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(@NonNull Call<List<ProductListingsModeResponsetTwo>> call, @NonNull Response<List<ProductListingsModeResponsetTwo>> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if(response.isSuccessful()) {
                            setAdapter(response.body());
                            Log.e("schdeulable products-----", String.valueOf(response.body().toString()));

                    }
                }

                @Override
                public void onFailure(Call<List<ProductListingsModeResponsetTwo>>  call, Throwable t) {
                    Log.e("onFailure",t.getLocalizedMessage());
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });
        }

    public void setAdapter(List<ProductListingsModeResponsetTwo> mFinalList){
            all_data_list = mFinalList;

            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new SchedulableItemsAdapter(getApplicationContext(), mFinalList);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.mcallBack = (SchedulableItemsAdapter.OnInterfacListener) this;
        }

    @Override
    public void onselectItem(int position, int quantity,int id) {
        try {
            if(quantity==0){
                checkoutlayout.setVisibility(View.GONE);
            }
            else
            {
                push_to_arrayList(position,quantity,id);
                checkoutlayout.setVisibility(View.VISIBLE);
            }

        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }

    public void push_to_arrayList(int position,int quant,int id){

        VirtualCartSingleton tmp = VirtualCartSingleton.getInstance();
        ProductListingsModeResponsetTwo productListingsModeResponse = all_data_list.get(position);


       if(mFinalList_for_cart.isEmpty()){
            productListingsModeResponse.setQuantity(String.valueOf(quant));
            mFinalList_for_cart.add(productListingsModeResponse);
            tmp.setmFinalList_for_cart(mFinalList_for_cart);
        }
        else
        {
            int i = 0;
            boolean found = false;
            for(Iterator<ProductListingsModeResponsetTwo> it = mFinalList_for_cart.iterator(); it.hasNext();) {
                ProductListingsModeResponsetTwo s = it.next();
              //  System.out.println(": " + s);
                if (s.getId() == id) {
                    found = true;
                    s.setQuantity(String.valueOf(quant));
                    tmp.setmFinalList_for_cart(mFinalList_for_cart);
                  break;
                }
            }
            if (!found) {
                productListingsModeResponse.setQuantity(String.valueOf(quant));
                mFinalList_for_cart.add(productListingsModeResponse);
                tmp.setmFinalList_for_cart(mFinalList_for_cart);
            }
        }
    }

    private Boolean mCheckInternetWithMultipleClicks(){

        if (CheckInternet.isNetwork(getApplicationContext())) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;
            //  }
        }else {
            //do something, net is not connected

            Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

        }

        return false;

    }
}
