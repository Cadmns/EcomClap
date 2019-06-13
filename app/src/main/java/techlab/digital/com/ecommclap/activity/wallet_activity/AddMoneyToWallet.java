package techlab.digital.com.ecommclap.activity.wallet_activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.NewCategoryActivity;
import techlab.digital.com.ecommclap.activity.PaytmGatweyActivity;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class AddMoneyToWallet extends AppCompatActivity {
Button add_money_btn;
EditText add_money_editText;
    Bundle bundle;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    private long mLastClickTime = 0;
    TextView available_bal_tv,money_msg_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_to_wallet);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        sessionManager= new SessionManager(getApplicationContext());
        setToolBar();
         bundle = getIntent().getExtras();

        available_bal_tv = findViewById(R.id.available_balance);
        available_bal_tv.setText("Available Balance : "+bundle.getString("wallet_money"));

        add_money_btn = findViewById(R.id.add_money_btn);
        add_money_editText = findViewById(R.id.amount_box);



        add_money_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (CheckInternet.isNetwork(getApplicationContext())) {
                  //  rechargeWallet(Integer.parseInt(String.valueOf(add_money_editText.getText())));


                    Intent intent = new Intent(getApplicationContext(), PaytmGatweyActivity.class);
                    intent.putExtra("money", validateAmount(add_money_editText.getText().toString()));
                    intent.putExtra("order_id",bundle.getInt("flow_flag"));
                    intent.putExtra("hasOrderId",false);




                    startActivityForResult(intent, 100);
                }
                else{
                    Toast.makeText(getApplicationContext(), String.valueOf(getResources().getText(R.string.no_internet_text)), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            if(resultCode == Activity.RESULT_OK){
                int order_id_=data.getIntExtra("result",0);

                rechargeWallet(Integer.parseInt(String.valueOf(add_money_editText.getText())),order_id_);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult



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


    private void rechargeWallet(final int amount, final int order_id){

        progressDialog = new ProgressDialog(this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call call = apiService.rechargeWallet("Bearer " + sessionManager.getKeySession(),sessionManager.getUserId(),amount,"Added to Your Wallet","credit");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if(response.isSuccessful()) {

                    System.out.println("wallet balance :"+response.body().toString());
                    showCustomDialog(String.valueOf(amount),order_id);

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


    private void showCustomDialog(String added_money, final int order_id_) {

        //before inflating the custom alert dialog highlight_remove, we will get the current activity viewgroup
        ViewGroup viewGroup =findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(AddMoneyToWallet.this).inflate(R.layout.show_success_transaction, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(AddMoneyToWallet.this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
     //   money_msg_tv.setText(added_money);
        Button btn = (Button) dialogView.findViewById(R.id.buttonOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               /* Intent intent = new Intent(getApplicationContext(),EcomWalletActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);






*/


                if (order_id_==-1 ){
                    Intent returnIntent = new Intent(getApplicationContext(), NewCategoryActivity.class);
                    startActivity(returnIntent);
                    finish();
                }

                if (order_id_==-2){

                    if (bundle.getInt("flow_flag")==-2){
                        Intent returnIntent = new Intent(getApplicationContext(), EcomWalletActivity.class);
                        returnIntent.putExtra("flow", "paytmSdk");
                        startActivity(returnIntent);
                        finish();
                    }


                }








            }
        });


        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    private String validateAmount(String strBalance){
      return   strBalance.replaceFirst("^0+(?!$)", "");
    }

}
