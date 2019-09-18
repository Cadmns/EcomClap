package techlab.digital.com.ecommclap.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.wallet_activity.EcomWalletActivity;
import techlab.digital.com.ecommclap.model.Checksum;
import techlab.digital.com.ecommclap.model.Paytm;
import techlab.digital.com.ecommclap.network.Api;
import techlab.digital.com.ecommclap.utility.Constants;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class PaytmGatweyActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String collectingMoney;
    int order_id_;
    //the textview in the interface where we have the price
    TextView textViewPrice;
    SessionManager sessionManager;
    Boolean hasOrderId;
    int temp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager= new SessionManager(getApplicationContext());
        Intent intent = getIntent();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        if (null != intent.getExtras()) {
            collectingMoney = getIntent().getExtras().getString("money");
            hasOrderId = getIntent().getExtras().getBoolean("hasOrderId");

            if (hasOrderId)
                order_id_ = getIntent().getExtras().getInt("order_id");
            else {
                order_id_ = (int) (Math.random() * 5000 + 1);
                temp_id = getIntent().getExtras().getInt("order_id");
            }
            generateCheckSum();
        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }



    }

    private void generateCheckSum() {

        //getting the tax amount first.
       String txnAmount = collectingMoney;
       // String txnAmount = "1";


        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL+order_id_,
                Constants.INDUSTRY_TYPE_ID,String.valueOf(order_id_),String.valueOf(sessionManager.getUserId()
        ));

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment(response.body().getChecksumHash(), paytm);
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

            }
        });
    }

    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        //PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());


        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);

    }

    //all these overriden method is to detect the payment result accordingly
    @Override
    public void onTransactionResponse(Bundle bundle) {

        Toast.makeText(this, "success", Toast.LENGTH_LONG).show();

        String txid = bundle.getString("ORDERID");
         Log.e("txid++++++++++++=",txid+"   hasOrderId    "+bundle.toString());
        if (!hasOrderId){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",temp_id);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }


        else {

        Intent intent = new Intent(getApplicationContext(), UpdatePaymentDetailsActivity.class);
        intent.putExtra("txid", txid);
        intent.putExtra("order_id", order_id_);
        startActivity(intent);

    }
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "Cancel transaction", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), NewCategoryActivity.class);
            startActivity(intent);
            finish();


    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();
        finish();
    }


    /*
     * The following method we are using to generate a random string everytime
     * As we need a unique customer id and order id everytime
     * For real scenario you can implement it with your own application logic
     * */
    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }


}