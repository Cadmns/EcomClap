package techlab.digital.com.ecommclap.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.MyAccountActivity;
import techlab.digital.com.ecommclap.activity.UserProfileActivity;
import techlab.digital.com.ecommclap.model.userDetails.Billing;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class UpdateBillingAdd extends AppCompatActivity {

    @BindView(R.id.update_address)
    Button update_address;
    @BindView(R.id.input_name_first)
    EditText mfirstname;
    @BindView(R.id.input_name_last)
    EditText lastname;
    @BindView(R.id.flat_no)
    EditText flat_number;
    @BindView(R.id.colony)
    EditText colony;
    @BindView(R.id.input_address2)
    EditText  mAddressLine2;
    @BindView(R.id.input_post_code)
    EditText pincode;
    @BindView(R.id.input_phoneNumber)
            EditText mPhoneNumber;
    SessionManager sessionManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_billing_add);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar();
        update_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCheckInternetWithMultipleClicks()) {
                    if (! mfirstname.getText().toString().matches("") && ! lastname.getText().toString().matches("")&& ! mPhoneNumber.getText().toString().matches("") && ! flat_number.getText().toString().matches("") && ! pincode.getText().toString().matches("") &&! mAddressLine2.getText().toString().matches("")) {
                        if(colony.getText().toString().matches(""))
                        {
                            colony.setText(" ");
                        }
                        update_address();

                    } else {
                        Toast.makeText(getApplicationContext(), "Enter all details", Toast.LENGTH_SHORT).show();

                    }
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


    public UserDetailsResponse get_User_From_Shared_Prefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("user_info", "");
        UserDetailsResponse user = gson.fromJson(json, UserDetailsResponse.class);
        return user;
    }

    UserDetailsResponse userDetailsResponse;
    private void initViews() {
        userDetailsResponse = get_User_From_Shared_Prefs(getApplicationContext());
        try {
            userDetailsResponse = get_User_From_Shared_Prefs(getApplicationContext());
            mfirstname = (EditText) findViewById(R.id.input_name_first);
            lastname = (EditText) findViewById(R.id.input_name_last);
            flat_number = (EditText) findViewById(R.id.flat_no);
            colony = (EditText) findViewById(R.id.colony);
            mAddressLine2 = (EditText) findViewById(R.id.input_address2);
            pincode = (EditText) findViewById(R.id.input_post_code);
            pincode.setText(userDetailsResponse.getBilling().getPostcode());
            mPhoneNumber.setText(userDetailsResponse.getBilling().getPhone());
            mfirstname.setText(userDetailsResponse.getBilling().getFirstName());
            lastname.setText(userDetailsResponse.getBilling().getLastName());
            String mAddress = String.valueOf(userDetailsResponse.getBilling().getAddress1());
            String[] animalsArray = mAddress.split(",");
            try {
                flat_number.setText(animalsArray[0]);
                colony.setText(animalsArray[1]);
                mAddressLine2.setText(animalsArray[2]);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "There is issues with your address formate", Toast.LENGTH_LONG).show();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    ProgressDialog progressDialog;
    private void update_address(){
        progressDialog = new ProgressDialog(UpdateBillingAdd.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();


        Billing regisReq= new Billing();
        userDetailsResponse.setFirstName(mfirstname.getText().toString());
        userDetailsResponse.setLastName(lastname.getText().toString());
        regisReq.setFirstName(mfirstname.getText().toString());
        regisReq.setLastName(lastname.getText().toString());
        regisReq.setPhone(mPhoneNumber.getText().toString());
        regisReq.setAddress1(is_string_contains_coma(flat_number.getText().toString())+","+is_string_contains_coma(colony.getText().toString())+","+is_string_contains_coma(mAddressLine2.getText().toString()));
        regisReq.setPostcode(pincode.getText().toString());

        userDetailsResponse.setBilling(regisReq);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.updateUserDetails("Bearer " + sessionManager.getKeySession(),userDetailsResponse,sessionManager.getUserId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    if(response.code()==200) {
                        Toast.makeText(getApplicationContext(),"Address Updated successfull",Toast.LENGTH_SHORT).show();
                        storeUserInfoSharedPref(getApplicationContext(),userDetailsResponse);
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                    else
                        Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();

                }else{
                    Log.e("Error","");
                }
                //  finish();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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

    public static final String PREFS_NAME = "EcommClap";
    // used for store arrayList in json format
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private void storeUserInfoSharedPref(Context context, UserDetailsResponse finalContestModel){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(finalContestModel);
        prefsEditor.putString("user_info", json);
        prefsEditor.apply();
    }
    private long mLastClickTime = 0;
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
}
