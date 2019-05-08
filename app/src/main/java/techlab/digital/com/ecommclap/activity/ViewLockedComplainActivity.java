package techlab.digital.com.ecommclap.activity;

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
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.ComplainListAdapter;
import techlab.digital.com.ecommclap.adapter.MyCouponAdapter;
import techlab.digital.com.ecommclap.model.complaints_model.ListComplaint;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

import static java.security.AccessController.getContext;

public class ViewLockedComplainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ComplainListAdapter mAdapter;
    TextView no_complain,titleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_locked_complain);

        no_complain = findViewById(R.id.no_complain);
        mRecyclerView = findViewById(R.id.RecyleViewComplain);
        sessionManager = new SessionManager(getApplicationContext());
        setToolBar();
        get_complain_list();

    }


    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText("Your Raised Complain");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }








    ProgressDialog progressDialog;
    private void get_complain_list(){

        progressDialog = new ProgressDialog(this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Payment..");
        progressDialog.setCancelable(false);
        progressDialog.show();




        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ListComplaint>> call = apiService.fetch_complaits_List("Bearer " + sessionManager.getKeySession());
        call.enqueue(new Callback<List<ListComplaint>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<List<ListComplaint>> call, @NonNull Response<List<ListComplaint>> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()) {

                  //  Log.e("cp Object to Json android-----", String.valueOf(response.body().get(0).getId()));
                    if(response.body().isEmpty())
                    {
                        no_complain.setVisibility(View.VISIBLE);
                    }else {
                        no_complain.setVisibility(View.INVISIBLE);
                        setAdapter(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ListComplaint>>  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }




    private void setAdapter(List<ListComplaint> mFinalList) {

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ComplainListAdapter(mFinalList,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);


    }

}
