package techlab.digital.com.ecommclap.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.auth.LoginActivity;
import techlab.digital.com.ecommclap.model.userDetails.Billing;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class UpdateUserDetails extends AppCompatActivity {
    @BindView(R.id.btn_signup)
    Button signUp_btn;

    @BindView(R.id.input_name)
    EditText mfirstname;
    @BindView(R.id.input_name_last)
    EditText lastname;
    @BindView(R.id.input_phoneNumber)
    EditText phone;

    @BindView(R.id.input_post_code)
    EditText pincode;
    @BindView(R.id.input_address2)
    EditText  mAddressLine2;

    @BindView(R.id.flat_no)
    EditText flat_number;
    @BindView(R.id.colony)
    EditText colony;


    RelativeLayout mRelativeLayout;
    TextView titleTextView;
    String mAddress;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_details);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        mRelativeLayout = findViewById(R.id.containerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sessionManager = new SessionManager(getApplicationContext());
        toolbar.setTitle("Update Details");
        initViews();
        setToolBar();
    }



    UserDetailsResponse userDetailsResponse;
    @SuppressLint("LongLogTag")
    private void initViews(){

        userDetailsResponse = get_User_From_Shared_Prefs(getApplicationContext());
        try {
            mfirstname.setText(userDetailsResponse.getBilling().getFirstName());
            lastname.setText(userDetailsResponse.getBilling().getLastName());
            mAddress = userDetailsResponse.getBilling().getAddress1();
            pincode.setText(userDetailsResponse.getBilling().getPostcode());
            phone.setText(userDetailsResponse.getBilling().getPhone());
            try {
                String[] animalsArray = mAddress.split(",");
                flat_number.setText(animalsArray[0]);
                colony.setText(animalsArray[1]);
                mAddressLine2.setText(animalsArray[2]);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "issues with your adddress formate", Toast.LENGTH_SHORT).show();
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    public UserDetailsResponse get_User_From_Shared_Prefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("user_info", "");


        return gson.fromJson(json, UserDetailsResponse.class);
    }

    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.title_update));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    @OnClick(R.id.btn_signup)
    public void submituserDetails(View view){
        String first_name = mfirstname.getText().toString();
        String last_name = lastname.getText().toString();
        String phone_no = phone.getText().toString();
        String pin_code = pincode.getText().toString();

        String flate_number = flat_number.getText().toString();
        String socity =colony.getText().toString();
        String maddress=mAddressLine2.getText().toString();


        if (! first_name.equals("")&& ! flate_number.equals("")&& ! socity.equals("")&& ! maddress.equals("")&& ! last_name.equals("") && ! phone_no.equals("")&& ! pin_code.equals("")) {
            if(socity.matches(""))
            {
                colony.setText(" ");
            }
            sendUserRegistrationDetails();
        }
        else {
           // Toast.makeText(getApplicationContext(), "Enter all details", Toast.LENGTH_SHORT).show();

            initSnackBar("Enter all details");
        }
    }


    private void initSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(mRelativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    ProgressDialog progressDialog;
    private void sendUserRegistrationDetails(){
        progressDialog = new ProgressDialog(UpdateUserDetails.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Log.e("sessionToken",sessionManager.getKeySession());
        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();


        Billing regisReq= new Billing();
        userDetailsResponse.setFirstName(mfirstname.getText().toString());
        userDetailsResponse.setLastName(lastname.getText().toString());
        regisReq.setFirstName(mfirstname.getText().toString());
        regisReq.setLastName(lastname.getText().toString());
        regisReq.setPhone(phone.getText().toString());
        regisReq.setPostcode(pincode.getText().toString());
        regisReq.setAddress1(is_string_contains_coma(flat_number.getText().toString())+","+is_string_contains_coma(colony.getText().toString())+","+is_string_contains_coma(mAddressLine2.getText().toString()));
        // regisReq.setState(state.getText().toString());


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

                      Toast.makeText(getApplicationContext(),"Update successfull",Toast.LENGTH_SHORT).show();


                        storeUserInfoSharedPref(getApplicationContext(),userDetailsResponse);
                        Intent returnIntent = new Intent();
                       // returnIntent.putExtra("result",result);
                        setResult(Activity.RESULT_OK,returnIntent);
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
        Log.e("update!!string with comma",commaString);
        Log.e("update----comma removed string",str);

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
