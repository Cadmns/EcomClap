package techlab.digital.com.ecommclap.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class UserAddress extends AppCompatActivity {
    TextView titleTextView;
    LinearLayout layoutCartNoItems,layoutCartLoginNeeded;
    CardView layoutItems;
    SessionManager sessionManager;
    @BindView(R.id.name)
    TextView userName;
    @BindView(R.id.address)
    TextView userAddress;
    @BindView(R.id.layout_action1)
    LinearLayout mEditLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_saved_address);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        setToolBar();
        setCartLayout();

    }

    @OnClick(R.id.layout_action1)
    public void  mEditLayout(View view){

        Intent intent = new Intent(getApplicationContext(),UpdateUserDetails.class);
        startActivityForResult( intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                setCartLayout();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private void setCartLayout(){
        layoutItems = (CardView) findViewById(R.id.cardview);
        layoutCartNoItems = (LinearLayout)findViewById(R.id.layout_cart_empty);
        layoutCartLoginNeeded = (LinearLayout)findViewById(R.id.layout_login_empty);

        if (sessionManager.isLoggedIn() && sessionManager.getUserId() != 0 ) {
            /*request server to fetch the cart items*/
            layoutItems.setVisibility(View.GONE);
            fetchUserDetails();
        }
        else {

            layoutCartLoginNeeded.setVisibility(View.VISIBLE);
            layoutItems.setVisibility(View.GONE);
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

    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.title_saved_address));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    ProgressDialog progressDialog;
    private void fetchUserDetails(){

        progressDialog = new ProgressDialog(UserAddress.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserDetailsResponse> call = apiService.fetchUserDetails(sessionManager.getUserId(),"Bearer " + sessionManager.getKeySession());

        call.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    layoutItems.setVisibility(View.VISIBLE);

                    userName.setText(response.body().getFirstName()+" "+response.body().getLastName());
                    userAddress.setText(response.body().getBilling().getAddress1()+" "+response.body().getBilling().getAddress2()+" "+response.body().getBilling().getCity()+" "+response.body().getBilling().getState()+" "+response.body().getBilling().getPostcode());


                }else{
                    Log.e("Error","");
                    if (response.code() == 403){


                      //  initSnackBar("Incorrect password");


                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
              //  initSnackBar(t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // finish();
            }
        });
    }
}
