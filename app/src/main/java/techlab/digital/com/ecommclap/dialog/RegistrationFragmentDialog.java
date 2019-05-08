package techlab.digital.com.ecommclap.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.auth.LoginActivity;
import techlab.digital.com.ecommclap.fragments.AddReferralCodeFragment;
import techlab.digital.com.ecommclap.fragments.ExpensesBottomSheet;
import techlab.digital.com.ecommclap.model.auth.RegistrationReq;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class RegistrationFragmentDialog extends AppCompatActivity {
    @BindView(R.id.btn_signup)
    Button signUp_btn;
    private static final String TAG = "RegistrationFragmentDialog";
    @BindView(R.id.input_name)
    EditText f_name;
    @BindView(R.id.input_name_last)
    EditText  l_name;
    @BindView(R.id.input_phoneNumber)
    EditText  mobile_no;
    @BindView(R.id.input_email)
    EditText email_id;
    @BindView(R.id.input_password)
    EditText password;
    @BindView(R.id.input_confirm_password)
    EditText confirm_password;
    @BindView(R.id.input_post_code)
    EditText post_code;
    @BindView(R.id.apply_referralCode)
    TextView mReferralCodeView;
    @BindView(R.id.flat_no)
    EditText flat_number;
    @BindView(R.id.colony)
    EditText colony;
    @BindView(R.id.input_address2)
    EditText  mAddressLine2;
    TextView titleTextView;
    RelativeLayout relativeLayout;
    // variable to track event time`
    private long mLastClickTime = 0;
    SessionManager sessionManager;
    AddReferralCodeFragment fragment;
    ProgressDialog progressDialog;
    String referralCode;
    @BindView(R.id.code_applied_layout)
    LinearLayout mcode_applied_layout;
    @BindView(R.id.reset_referal)
    TextView reset_referal;
    @BindView(R.id.referal_applied_succesfully)
    TextView applied_referal_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        sessionManager = new SessionManager(getApplicationContext());
        relativeLayout = findViewById(R.id.containerView);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registration");
        invokeBroadcastRegister();
        setToolBar();
        mobile_no.setText(""+sessionManager.getContactNumber());



        reset_referal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_referal();
            }
        });

    }


    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.title_registartion));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @OnClick(R.id.apply_referralCode)
    public void onReferralCodeViewClick(){

        if (mCheckInternetWithMultipleClicks()) {
            inVokeReferralCode();
        }
    }

    @OnClick(R.id.btn_signup)
    public void submituserDetails(View view){

        if (mCheckInternetWithMultipleClicks()) {
            if (! email_id.getText().toString().matches("") && ! password.getText().toString().matches("") && ! confirm_password.getText().toString().matches("") && ! f_name.getText().toString().matches("") &&  ! l_name.getText().toString().matches("")  && ! flat_number.getText().toString().matches("") && ! post_code.getText().toString().matches("")) {
                if(password.getText().toString().matches(confirm_password.getText().toString())) {

                    if(colony.getText().toString().matches(""))
                    {
                        colony.setText(" ");
                    }
                    sendUserRegistrationDetails();
                }
                else
                {
                    initSnackBar("Confirm Paasword is not matched");
                }
            } else {
                initSnackBar("Enter all details");
            }
        }

    }

    private Boolean mCheckInternetWithMultipleClicks(){

        if (CheckInternet.isNetwork(getApplicationContext())) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;

        }else {
            //do something, net is not connected

            Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

        }

        return false;

    }

    private void initSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(relativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }


    private void sendUserRegistrationDetails(){
        RegistrationReq regisReq= new RegistrationReq();
        regisReq.setEmail(email_id.getText().toString());
        regisReq.setPassword(password.getText().toString());
        regisReq.setAddress(is_string_contains_coma(flat_number.getText().toString())+","+ is_string_contains_coma(colony.getText().toString())+","+ is_string_contains_coma(mAddressLine2.getText().toString()));

        regisReq.setRefer_id(referralCode);
        regisReq.setFirstName(f_name.getText().toString());
        regisReq.setLastName(l_name.getText().toString());
        regisReq.setPhone(mobile_no.getText().toString());
        regisReq.setPincode(post_code.getText().toString());
        // regisReq.setState(state.getText().toString());

        progressDialog = new ProgressDialog(this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call call = apiService.submitUserDetails(regisReq);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    if(response.code()==200) {

                        Log.e("response message", String.valueOf(response.body()));
                        if (response.body().equals("Sorry, that username already exists!")){

                            initSnackBar("Sorry, that username already exists!");

                        }
                        else {

                            Toast.makeText(getApplicationContext(), "You Have success fully Registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
                }else{
                    Log.e("Error","");
                }
                //  finish();
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }


    @SuppressLint("LongLogTag")
    public String is_string_contains_coma(String commaString){
        String str;

        str = commaString.replaceAll(",", "");

        return str;
    }

    private void inVokeReferralCode(){
        fragment = new AddReferralCodeFragment();
        fragment.show(getSupportFragmentManager(), "TAG");

    }


    private void reset_referal(){

        mReferralCodeView.setVisibility(View.VISIBLE);
        mcode_applied_layout.setVisibility(View.GONE);
        referralCode = "";
    }



    BroadcastReceiver someBroadcastReceiver;
    private void invokeBroadcastRegister(){
   someBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //TODO extract extras from intent
                String code = intent.getStringExtra("code");

                mReferralCodeView.setVisibility(View.GONE);
                mcode_applied_layout.setVisibility(View.VISIBLE);
                applied_referal_textview.setText("Referal code "+code+" is applied succesfully");
                referralCode = code;
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(someBroadcastReceiver,
                new IntentFilter("referralCode"));
    }
    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(someBroadcastReceiver);
        super.onPause();
    }


}