package techlab.digital.com.ecommclap.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.update_order.UpdateCartPayment;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class UpdatePaymentDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    SessionManager sessionManager;
    TextView titleTextView;
    @BindView(R.id.header_message)
    TextView mHeaderMessage;
    @BindView(R.id.page_slogan)
    TextView mPageSloganMessage;
    String txId;
    int order_id_;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_payment_details);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        Intent intent = getIntent();
        if (null != intent.getExtras()) {
            if (getIntent().hasExtra("txid"))
            txId = getIntent().getExtras().getString("txid");
            order_id_ = getIntent().getExtras().getInt("order_id");

        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        setToolBar();
        initViews();

        if (getIntent().hasExtra("txid"))
        setListeners();
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),NewCategoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }


    private void setListeners(){


                                        if (!CheckInternet.isNetwork(getApplicationContext())) {
                                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.connect_to_internet),Toast.LENGTH_SHORT).show();
                                            mPageSloganMessage.setText(getResources().getString(R.string.internet_message));


                                            return;
                                        }

                                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                                            return;
                                        }
                                        mLastClickTime = SystemClock.elapsedRealtime();

                                        updatePlacedOrder(txId,order_id_);

    }

    private void initViews(){
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        Typeface custom_fonts = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        mHeaderMessage.setTypeface(custom_font);
        mHeaderMessage.setText(getResources().getString(R.string.almost_there));
        mPageSloganMessage.setTypeface(custom_fonts);
        mPageSloganMessage.setText(getResources().getString(R.string.recommend_message)+" and your Order id : "+order_id_);
    }

    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.add_order_status));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onRefresh() {
        if (!CheckInternet.isNetwork(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.connect_to_internet),Toast.LENGTH_SHORT).show();

            return;
        }

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();


        updatePlacedOrder(txId, order_id_);

    }
    ProgressDialog progressDialog;
    private void updatePlacedOrder(String txId, int order_id_) {

        UpdateCartPayment updateCartPayment = new UpdateCartPayment();
        updateCartPayment.setStatus("processing");
        updateCartPayment.setTransaction_id(txId);
        progressDialog = new ProgressDialog(UpdatePaymentDetailsActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.updatePlacedOrder("Bearer " + sessionManager.getKeySession(),updateCartPayment,order_id_);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){

                   Toast.makeText(getApplicationContext(),"update successfully",Toast.LENGTH_SHORT).show();



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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
