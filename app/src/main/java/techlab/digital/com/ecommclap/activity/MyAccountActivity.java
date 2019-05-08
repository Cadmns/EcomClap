package techlab.digital.com.ecommclap.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.dialog.UpdateBillingAdd;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class MyAccountActivity extends AppCompatActivity {
Button edit_billing_addBtn;
    SessionManager sessionManager;

    @BindView(R.id.profile_name)
    TextView profile_username;
    @BindView(R.id.profile_email)
    TextView profile_useremail;
    @BindView(R.id.profile_number)
    TextView profile_usernumber;
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
    @BindView(R.id.myaccount_layout_container)
    RelativeLayout mlayout_container;
    @BindView(R.id.input_phoneNumber)
    EditText mPhonenNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        sessionManager = new SessionManager(getApplicationContext());
        ButterKnife.bind(this);
        initViews();
        fecthUserDetailsFromSharedPef();

        edit_billing_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateBillingAdd.class);
                startActivity(intent);


            }
        });
    }

    ProgressDialog progressDialog;



    public UserDetailsResponse get_User_From_Shared_Prefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("user_info", "");
        UserDetailsResponse user = gson.fromJson(json, UserDetailsResponse.class);
        return user;
    }

/* fetching user information from shared prefrence to avoid network call */
    @SuppressLint("LongLogTag")
    private void fecthUserDetailsFromSharedPef() {

    UserDetailsResponse user_from_shared_prefs = get_User_From_Shared_Prefs(getApplicationContext());
    if (user_from_shared_prefs.getBilling().getFirstName().isEmpty()) {
            fetchUserDetails();
    } else {
        try {
            profile_username.setText(user_from_shared_prefs.getBilling().getFirstName() + "  " + user_from_shared_prefs.getBilling().getLastName());
            profile_useremail.setText(sessionManager.getUserEmail());
            profile_usernumber.setText(sessionManager.getContactNumber());
            mPhonenNumber.setText(user_from_shared_prefs.getBilling().getPhone());

            pincode.setText(user_from_shared_prefs.getBilling().getPostcode());
            mfirstname.setText(user_from_shared_prefs.getBilling().getFirstName() + " ");
            lastname.setText("" + user_from_shared_prefs.getBilling().getLastName());
            String mAddress = user_from_shared_prefs.getBilling().getAddress1();
            try {
                String[] animalsArray = mAddress.split(",");
                flat_number.setText(animalsArray[0]);
                colony.setText(animalsArray[1]);
                mAddressLine2.setText(animalsArray[2]);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "issues with your adddress formate", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
    public void initViews(){
        edit_billing_addBtn = findViewById(R.id.edit_billing_add);

    }


    private void storeUserInfoSharedPref(Context context, UserDetailsResponse finalContestModel){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(finalContestModel);
        prefsEditor.putString("user_info", json);
        prefsEditor.apply();

        fecthUserDetailsFromSharedPef();

    }

    private void fetchUserDetails(){


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserDetailsResponse> call = apiService.fetchUserDetails(sessionManager.getUserId(),"Bearer " + sessionManager.getKeySession());

        call.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                if(response.isSuccessful()){
                    storeUserInfoSharedPref(getApplicationContext(),response.body());
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

            }
        });
    }
}
