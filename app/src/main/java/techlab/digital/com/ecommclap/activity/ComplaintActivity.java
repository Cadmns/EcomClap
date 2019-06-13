package techlab.digital.com.ecommclap.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.CreateTicketModel.CreateTicketReq;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ComplaintActivity extends AppCompatActivity {
TextView cannot_find_item,price_are_high,too_less_info,others,titleTextView;
LinearLayout linearLayout;
EditText message;
    Button submitEmail,view_complain;
SessionManager sessionManager;
ViewFlipper viewFlipper;
int order_id,key;
    String title;
    Editable content;

    private long mLastClickTime = 0;
String userEmail,userName,userPhone,userlastname,complainSubject,complainText,mAddress;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        sessionManager = new SessionManager(getApplicationContext());

        Intent intent = getIntent();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        view_complain = findViewById(R.id.view_complains_List);
        submitEmail=findViewById(R.id.submitEmail);
        linearLayout=findViewById(R.id.messageLayout);
        message=findViewById(R.id.message);
        cannot_find_item=findViewById(R.id.cant_find_item);
        price_are_high=findViewById(R.id.price_to_high);
        too_less_info=findViewById(R.id.too_less_info);
        others=findViewById(R.id.others);
        setToolBar();

        if (null != intent.getExtras()) {
            //order_id_ = getIntent().getExtras().getString("order_id");
            order_id= getIntent().getExtras().getInt("order_id");
            //key= getIntent().getExtras().getInt("key_value");
            // order_id_ = (String) getIntent().getExtras().get("order_id");
            Log.e("order_id", String.valueOf(order_id));
        }else {

            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }
        if(order_id!=-1)
        {
            linearLayout.setVisibility(View.VISIBLE);
            others.setBackgroundResource(R.drawable.rounded_view);
            cannot_find_item.setBackgroundResource(R.drawable.rounded_button);
            price_are_high.setBackgroundResource(R.drawable.rounded_button);
            too_less_info.setBackgroundResource(R.drawable.rounded_button);
            title= (String) others.getTag();
            message.setHint(title);
            editTextView(title);
        }
        view_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckInternetWithMultipleClicks()) {
                    Intent intent = new Intent(getApplicationContext(), ViewLockedComplainActivity.class);
                    startActivity(intent);
                }
            }
        });
        submitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCheckInternetWithMultipleClicks()) {
                    /*Log.e("tile",title);
                   Log.e("message", String.valueOf(message.getText()));
                   Log.e("content", String.valueOf(content));*/
                    //createTicket();
                    if (! message.getText().toString().matches("") && ! title.toString().matches("")) {
                        Editable messagee=message.getText();
                        // Log.e("messageContent", String.valueOf(messagee));
                        createTicket(String.valueOf(messagee),title);
                    } else {
                        Toast.makeText(ComplaintActivity.this, "Plz Write Something", Toast.LENGTH_SHORT).show();
                        // initSnackBar("Enter all details");
                    }
                }
            }
        });
        fecthUserDetailsFromSharedPef();
      //  FetchImageList();
        cannot_find_item.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                cannot_find_item.setBackgroundResource(R.drawable.rounded_view);
                others.setBackgroundResource(R.drawable.rounded_button);
                price_are_high.setBackgroundResource(R.drawable.rounded_button);
                too_less_info.setBackgroundResource(R.drawable.rounded_button);
                title= (String)cannot_find_item.getText();
                message.setHint(title);
                editTextView(title);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                others.setBackgroundResource(R.drawable.rounded_view);
                cannot_find_item.setBackgroundResource(R.drawable.rounded_button);
                price_are_high.setBackgroundResource(R.drawable.rounded_button);
                too_less_info.setBackgroundResource(R.drawable.rounded_button);
                title= (String) others.getText();
                message.setHint(title);
                editTextView(title);
            }
        });
        price_are_high.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                price_are_high.setBackgroundResource(R.drawable.rounded_view);
                others.setBackgroundResource(R.drawable.rounded_button);
                cannot_find_item.setBackgroundResource(R.drawable.rounded_button);
                too_less_info.setBackgroundResource(R.drawable.rounded_button);
                title= (String) price_are_high.getText();
                message.setHint(title);
                editTextView(title);
            }
        });
        too_less_info.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                too_less_info.setBackgroundResource(R.drawable.rounded_view);
                others.setBackgroundResource(R.drawable.rounded_button);
                price_are_high.setBackgroundResource(R.drawable.rounded_button);
                cannot_find_item.setBackgroundResource(R.drawable.rounded_button);
                //too_less_info.setBackgroundColor(R.drawable.rounded_view);
                title= (String) too_less_info.getText();
                message.setHint(title);
                editTextView(title);
            }
        });

    }
    public void editTextView(final String query){
        message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    message.getText().clear();
                    content = message.getText();
                }
                else {
                    message.setHint(query);
                }
            }
        });
    }
    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.Complaint_query));
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
    private void fecthUserDetailsFromSharedPef(){
        UserDetailsResponse user_from_shared_prefs = get_User_From_Shared_Prefs(getApplicationContext());
       // profile_username.setText(user_from_shared_prefs.getBilling().getFirstName()+"  "+user_from_shared_prefs.getBilling().getLastName());
        userEmail=sessionManager.getUserEmail();
        userPhone=sessionManager.getContactNumber();
        userName=user_from_shared_prefs.getBilling().getFirstName();
        userlastname=user_from_shared_prefs.getBilling().getLastName();
        mAddress = user_from_shared_prefs.getBilling().getAddress1();
        Log.e("user_deatails",userEmail+" "+userPhone+" "+userName+" "+userlastname+" "+mAddress+" "+order_id);

    }


    public void sendEmail() {
    Intent i = new Intent(Intent.ACTION_SEND);
    i.setType("message/rfc822");
    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"anil10112kumar@gmail.com"});
    i.putExtra(Intent.EXTRA_SUBJECT, "product problem");
    i.putExtra(Intent.EXTRA_TEXT, "Testing testing");
    try {
      //  startActivity(Intent.createChooser(i, "Send mail..."));
    } catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(ComplaintActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
    }
}
    ProgressDialog progressDialog;
    public void createTicket(String message, String title){
        progressDialog = new ProgressDialog(ComplaintActivity.this);
        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        CreateTicketReq createTicketReq=new CreateTicketReq(title,message,String.valueOf(sessionManager.getUserId()),String.valueOf(order_id));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call call = apiService.createTickets("Bearer " + sessionManager.getKeySession(),createTicketReq);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call,@NonNull Response response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    if(response.body()!=null)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            Log.e("TAGjason object", String.valueOf(jsonObject));
                            String result = String.valueOf(jsonObject.get("id"));
                            Log.e("TAGjason object id", String.valueOf(result));
                            result = result.substring(0, result.indexOf("."));
                            Log.e("iNTEGER ID WILL BE ", String.valueOf(result));
                            showCustomDialog(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(),"Your Complain & Query Sucessfully Submited",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"data not found",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"server error",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.e("onFailureOffers",t.getMessage());
            }
        });
    }
    private Boolean mCheckInternetWithMultipleClicks(){

        if (CheckInternet.isNetwork(getApplicationContext())) {
            /* if (sessionManager.isLoggedIn()) {*/
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

    private void showCustomDialog(String message) {
        //before inflating the custom alert dialog highlight_remove, we will get the current activity viewgroup
        ViewGroup viewGroup =findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(ComplaintActivity.this).inflate(R.layout.complain_succes_dialog, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintActivity.this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        //   money_msg_tv.setText(added_money);
        TextView complaints_id_tv = dialogView.findViewById(R.id.complaints_id);
        Button btn = (Button) dialogView.findViewById(R.id.buttonOk);
        complaints_id_tv.setText("Complaints ID:"+message);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NewCategoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }




}
