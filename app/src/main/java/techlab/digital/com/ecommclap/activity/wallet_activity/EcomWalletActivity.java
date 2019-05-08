package techlab.digital.com.ecommclap.activity.wallet_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.NewCategoryActivity;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class EcomWalletActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    TextView wallet_bal,reward_bal;
    CardView primary_wallet;
    private long mLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecom_wallet);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        getIntentValue();
        sessionManager= new SessionManager(getApplicationContext());
        wallet_bal = findViewById(R.id.wallet_bal);
        primary_wallet = findViewById(R.id.primary_wallet);
        reward_bal = findViewById(R.id.reward_bal_tv);
        fetchWalletBalance();
        fetchUserReward();
        setToolBar();
        primary_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (CheckInternet.isNetwork(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), ViewPrimaryWallet.class);
                    Bundle bundle = new Bundle();
                    //Add your data from getFactualResults method to bundle
                    bundle.putString("mamount", String.valueOf(wallet_bal.getText()));
                    //Add the bundle to the intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), String.valueOf(getResources().getText(R.string.no_internet_text)), Toast.LENGTH_SHORT).show();
                }
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


    private void fetchWalletBalance(){

        progressDialog = new ProgressDialog(this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call call = apiService.getWalletBalance("Bearer " + sessionManager.getKeySession(),sessionManager.getUserId());

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if(response.isSuccessful()) {
                    wallet_bal.setText(response.body().toString());
                    System.out.println("wallet balance :"+response.body().toString());
                }
            }

            @Override
            public void onFailure(Call  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }

    private void fetchUserReward(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Object> call = apiService.getUserReward("Bearer " + sessionManager.getKeySession(),sessionManager.getUserId());
        call.enqueue(new Callback<Object>(){
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if(response.isSuccessful()) {
                    reward_bal.setText(response.body().toString());
                }
            }
            @Override
            public void onFailure(Call <Object> call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {


        if (flow.equals("paytmSdk")){

            Intent intent = new Intent(getApplicationContext(), NewCategoryActivity.class);
            startActivity(intent);
        }else {
            super.onBackPressed();
        }



    }

    String flow;
    private void getIntentValue(){

        Intent intent = getIntent();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (null != intent.getExtras()) {
            flow = getIntent().getExtras().getString("flow");

        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
