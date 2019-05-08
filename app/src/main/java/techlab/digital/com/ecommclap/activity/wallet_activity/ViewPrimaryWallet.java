package techlab.digital.com.ecommclap.activity.wallet_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.WalletHistoryAdapter;
import techlab.digital.com.ecommclap.model.wallet.WalletHistory;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ViewPrimaryWallet extends AppCompatActivity {
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    WalletHistoryAdapter mAdapter;
    RecyclerView recyclerView;
    TextView wallet_ammount;
    CardView addMoney;
    private long mLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_primary_wallet);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        sessionManager= new SessionManager(getApplicationContext());
        recyclerView =  findViewById(R.id.history_recycler_view);
        addMoney = findViewById(R.id.add_money);
        wallet_ammount = findViewById(R.id.ammount);

        Bundle bundle = getIntent().getExtras();
        //Extract the data from bundle…
        wallet_ammount.setText( bundle.getString("mamount"));



        fetchWalletHistory();

       setToolBar();
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (CheckInternet.isNetwork(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), AddMoneyToWallet.class);
                    Bundle bundle = new Bundle();
                    //Add your data from getFactualResults method to bundle
                    bundle.putString("wallet_money", String.valueOf(wallet_ammount.getText()));
                    bundle.putInt("flow_flag",-2);
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




    private void fetchWalletHistory(){

        progressDialog = new ProgressDialog(this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonArray> call = apiService.getWalletHistories("Bearer " + sessionManager.getKeySession(),sessionManager.getUserId());

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if(response.isSuccessful()) {

                    try {
                        JSONArray jsonArray = new JSONArray(String.valueOf(response.body()));


                        if (jsonArray.isNull(0)){

                            Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            genratingList(jsonArray);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<JsonArray>  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }

    List<WalletHistory> mFinalResponse;
    private void genratingList(JSONArray jsonArray){
        ArrayList<WalletHistory> mFinalList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<WalletHistory>>(){}.getType());
        setWalletHistoryAdapter(mFinalList);
        /*Now the above list is contains the wallet history*/

    }

    public void setWalletHistoryAdapter(List<WalletHistory> walletHistoryList) {
        mAdapter = new WalletHistoryAdapter(getApplicationContext(), walletHistoryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }




}
