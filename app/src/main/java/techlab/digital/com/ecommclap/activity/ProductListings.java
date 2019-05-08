package techlab.digital.com.ecommclap.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.ProductListingsAdapter;
import techlab.digital.com.ecommclap.model.categories.subCategories.FetchSubCategory;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ProductListings extends AppCompatActivity {
    int product_id;
    String category_name;
    @BindView(R.id.toolbarTitle)
    TextView mToolBarName;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.noResults)
    TextView noResults;
    SessionManager sessionManager;
    // variable to track event time`
    private long mLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listings);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());

        Intent intent = getIntent();

        if (null != intent.getExtras()) {
            product_id =getIntent().getExtras().getInt("id");
            category_name =getIntent().getExtras().getString("category_name");

            settoolbar();

            if (CheckInternet.isNetwork(getApplicationContext())) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    fetchproducts(category_name);
            }else {
                //do something, net is not connected
                Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void settoolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolBarName = toolbar.findViewById(R.id.toolbarTitle);
        mToolBarName.setText(category_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    ProgressDialog progressDialog;

    private void fetchproducts(String str){


        progressDialog = new ProgressDialog(ProductListings.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);

            progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductListingsModeResponse>> call = apiService.getProducts(str);

        call.enqueue(new Callback<List<ProductListingsModeResponse>>() {
            @Override
            public void onResponse(Call<List<ProductListingsModeResponse>> call, Response<List<ProductListingsModeResponse>> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){

                    setAdapterViews(response.body());
                }else{
                    Log.e("Error","");

                }
            }

            @Override
            public void onFailure(Call<List<ProductListingsModeResponse>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });

    }


    ProductListingsAdapter mAdapter;
    private void setAdapterViews(List<ProductListingsModeResponse> datumList){
        mAdapter = new ProductListingsAdapter(getApplicationContext(), datumList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


}
