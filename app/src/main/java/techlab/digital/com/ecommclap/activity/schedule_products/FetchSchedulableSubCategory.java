package techlab.digital.com.ecommclap.activity.schedule_products;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.MySchedulableItemAdapter;
import techlab.digital.com.ecommclap.model.categories.subCategories.FetchSubCategory;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class FetchSchedulableSubCategory extends AppCompatActivity {
    SessionManager sessionManager;
    RecyclerView recyclerView;
    MySchedulableItemAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_schedulable_sub_category);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView= findViewById(R.id.recyclerview);
        setToolBar();
        fetch_subCategory();



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



    ProgressDialog progressDialog;
    public void fetch_subCategory(){
            progressDialog = new ProgressDialog(this);
            // Setting up message in Progress dialog.
            progressDialog.setMessage("loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<FetchSubCategory>> call = apiService.getSchedulable_subCategory(0);
            call.enqueue(new Callback<List<FetchSubCategory>>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(@NonNull Call<List<FetchSubCategory>> call, @NonNull Response<List<FetchSubCategory>> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if(response.isSuccessful()) {
                        createAdapter(response.body());
                        Log.e("schdeulable products-----", String.valueOf(response.body().toString()));

                    }
                }

                @Override
                public void onFailure(Call<List<FetchSubCategory>>  call, Throwable t) {
                    Log.e("onFailure",t.getLocalizedMessage());
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });
        }


    private void createAdapter(List<FetchSubCategory> body){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        // recyclerView.setHasFixedSize(true);
        mAdapter = new MySchedulableItemAdapter(this.getApplicationContext(), body);
        recyclerView.setAdapter(mAdapter);

    }


}
