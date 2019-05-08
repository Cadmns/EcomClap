package techlab.digital.com.ecommclap.activity.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.NewCategoryActivity;
import techlab.digital.com.ecommclap.dialog.RegistrationFragmentDialog;
import techlab.digital.com.ecommclap.model.auth.LoginReq;
import techlab.digital.com.ecommclap.model.auth.LoginResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class LoginActivity extends AppCompatActivity {
    // Session Manager Class
    SessionManager session;
    @BindView(R.id.btn_login)
    Button mLogin;
    @BindView(R.id.link_signup)
    TextView mSignUp;

    @BindView(R.id.input_email)
    EditText mUserName;
    @BindView(R.id.input_password)
    EditText mPassword;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.forgotPassword)
    TextView mForgotPassword;
        CoordinatorLayout mCoordinatorLayout;
    // variable to track event time
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // Session Manager
        session = new SessionManager(getApplicationContext());
        session.setFirstTimeLaunch(false);
        ButterKnife.bind(this);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .container);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mUserName.setText(""+session.getContactNumber());


        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(getApplicationContext(), "working on forgot password", Toast.LENGTH_SHORT).show();*/
                Intent  intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });
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

    @OnClick(R.id.btn_login)
    public void checkLoginCred(View view){
        // Log.e("login name",mUserName.getText().toString());

        if (! mUserName.getText().toString().matches("") && ! mPassword.getText().toString().matches("")) {
            if (mCheckInternetWithMultipleClicks())
                submitUserDetails1();
        }
        else {


            initSnackBar("Enter all details");
        }



    }


    @OnClick(R.id.link_signup)
    public void newSignUp(View view){
        if (mCheckInternetWithMultipleClicks()) {
            Intent intent = new Intent(getApplicationContext(), RegistrationFragmentDialog.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.forgotPassword)
    public void forgotPassword(View view){
        if (mCheckInternetWithMultipleClicks())
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

    }


    ProgressDialog progressDialog; String api;

    private void submitUserDetails1(){
        LoginReq loginReq = new LoginReq();
        loginReq.setPhone(mUserName.getText().toString());
        loginReq.setPassword(mPassword.getText().toString());


        progressDialog = new ProgressDialog(LoginActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.getToken(loginReq);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){

                    initSnackBar("Login Successfull");

                    if (response.body() != null) {
                        session.createLoginSession(response.body().getUserDisplayName(),response.body().getUserEmail(),response.body().getUser_id(),response.body().getToken());
                       /* Intent intent = new Intent(getApplicationContext(), NewCategoryActivity.class);
                        startActivity(intent);*/
                        Intent intent = new Intent(getApplicationContext(), NewCategoryActivity.class);
                        startActivity(intent);
                    }



                }else{
                    Log.e("Error","");
                    if (response.code() == 403){
                        initSnackBar("Incorrect password");

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                initSnackBar(t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
               // finish();
            }
        });

    }


    private void initSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();

    }



}
