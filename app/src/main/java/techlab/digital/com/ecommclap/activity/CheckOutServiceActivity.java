package techlab.digital.com.ecommclap.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.fragments.ExpensesBottomSheet;
import techlab.digital.com.ecommclap.fragments.LoadMyOffersBottomSheet;
import techlab.digital.com.ecommclap.fragments.LowBalanceBottomSheet;
import techlab.digital.com.ecommclap.model.GetTotalPayable;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.orderPlaced.OrderResponse;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.model.walletDeduction.WalletDeductReq;
import techlab.digital.com.ecommclap.model.walletDeduction.WalletDeductedResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class CheckOutServiceActivity extends AppCompatActivity {
    @BindView(R.id.apply_promocode)
    TextView apply_promocode;
    SessionManager sessionManager;
    @BindView(R.id.scrollView)
    ScrollView mScriollView;
    ProductListingsModeResponse productResponse;
    @BindView(R.id.layout_action1)
    LinearLayout mEditLayout;
    @BindView(R.id.image_cartlist)
    public ImageView thumbnail;
    @BindView(R.id.item_Name)
    public TextView mTitle;
    @BindView(R.id.item_price)
    public TextView mPrice;
    TextView titleTextView;
    @BindView(R.id.name)
    TextView userName;
    @BindView(R.id.description)
    TextView userDescription;
    @BindView(R.id.address)
    TextView userAddress;
    private RadioGroup radioGroup_select_env;
    Boolean mBtn_flag=false;
    String mbookingDate;
    RadioButton cod,instantPayment,walletPay;
    @BindView(R.id.btnAddExpense)
    Button ExpensesDetailsBtn;
    LinearLayout order_money_layout;
    @BindView(R.id.btn_signup)
    Button checkout_btn;
    String mTotalCartAmount;
    @BindView(R.id.promo_code_applied)
    LinearLayout promo_code_is_applied;
    @BindView(R.id.coupon_apllied_success)
    TextView coupon_succesfully_applied;
    @BindView(R.id.remove_applied_coupon)
    TextView remove_promo_code;
    @BindView(R.id.reward_continer)
    LinearLayout mRewardContainer;
    // variable to track event time`
    private long mLastClickTime = 0;
    CheckBox reward_checkbox;
    Double total_payable_amount_;
    String wallet_bal,current_wallet_balance,current_total_reward_point,Point_appleid,total_deduction,total_payable_amount="";
    @BindView(R.id.select_dates_check_box)
    CheckBox select_dates_check_box;
    @BindView(R.id.first_date)
    TextView first_date;
    @BindView(R.id.second_date)
    TextView second_date;
    @BindView(R.id.dates_container)
    LinearLayout mdates_container;
    private final Calendar myCalendar1 = Calendar.getInstance();
    private final Calendar myCalendar2 = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_service);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        final RadioGroup radio = (RadioGroup) findViewById(R.id.radio_grp_env);
        order_money_layout = findViewById(R.id.balance_container);
        reward_checkbox = (CheckBox)findViewById(R.id.mcheckBox);

        setToolBar();
        Intent intent = getIntent();
        if (null != intent.getExtras()) {
            mbookingDate = getIntent().getExtras().getString("service_date");
            productResponse = getIntent().getExtras().getParcelable("object");
            mTotalCartAmount = productResponse.getPrice();
            init();
            getTotalPayableAmount();

        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }


        ExpensesDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpensesBottomSheet fragment = new ExpensesBottomSheet(current_wallet_balance,current_total_reward_point,Point_appleid,total_deduction,total_payable_amount,userDetailsResponse.getBilling().getAddress1()+" "+userDetailsResponse.getBilling().getAddress2()+" "+userDetailsResponse.getBilling().getCity()+" "+userDetailsResponse.getBilling().getState()+" "+userDetailsResponse.getBilling().getPostcode());
                fragment.show(getSupportFragmentManager(), "TAG");
            }
        });

        apply_promocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadMyOffersBottomSheet fragment_obj = new LoadMyOffersBottomSheet(getApplicationContext());
                /*  fragment_obj.setCancelable(false);*/
                fragment_obj.show(getSupportFragmentManager(), "TAG");


            }
        });



        first_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_first_date();
            }
        });

        second_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_second_date();


            }
        });
        select_dates_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(select_dates_check_box.isChecked()){
                    //select_dates_check_box.toggle();
                    mdates_container.setVisibility(View.VISIBLE);
                }else {
                    mdates_container.setVisibility(View.GONE);
                }

            }
        });







        remove_promo_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promo_code_is_applied.setVisibility(View.GONE);
                apply_promocode.setVisibility(View.VISIBLE);


                applied_code_="";
                applied_code_amount="";
                applied_code_id="";
            }
        });


        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);
                switch (index) {
                    case 0:
                        order_money_layout.setVisibility(View.GONE);
                        break;
                    case 1:
                        order_money_layout.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        order_money_layout.setVisibility(View.VISIBLE);
                        if (!isNullOrEmpty(current_wallet_balance) && !isNullOrEmpty(mTotalCartAmount))
                            _compare_wallet_to_netPayable(current_wallet_balance,mTotalCartAmount);
                        else {
                            Toast.makeText(getApplicationContext(),"internet connectivity is too low",Toast.LENGTH_SHORT).show();
                        }

                        break;
                }

            }
        });
        reward_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){


                    if (!isNullOrEmpty(total_deduction) ){
                        double netPyable = Double.valueOf(mTotalCartAmount);
                        double reward_applied = Double.valueOf(total_deduction);
                        checkout_btn.setText("Checkout ₹ "+(netPyable-reward_applied));}


                } else {
                    checkout_btn.setText("Checkout ₹ "+mTotalCartAmount);

                }
            }
        });
    }

    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            view.setMinDate(System.currentTimeMillis() - 1000);
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            // TODO Auto-generated method stub
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, monthOfYear);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            first_date.setText(sdf.format(myCalendar1.getTime()));
        }

    };

    public void set_first_date() {

        DatePickerDialog mDate = new DatePickerDialog(CheckOutServiceActivity.this, date1, myCalendar1.get(Calendar.YEAR),  myCalendar1.get(Calendar.MONTH),  myCalendar1.get(Calendar.DAY_OF_MONTH));
        mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDate.show();
        /*new DatePickerDialog(CheckOutServiceActivity.this, date1, myCalendar1
                .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                myCalendar1.get(Calendar.DAY_OF_MONTH)).show();*/
    }

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            view.setMinDate(System.currentTimeMillis() - 1000);
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            second_date.setText(sdf.format(myCalendar2.getTime()));
        }

    };

    public void set_second_date() {

      /*  new DatePickerDialog(CheckOutServiceActivity.this, date2, myCalendar2
                .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                myCalendar2.get(Calendar.DAY_OF_MONTH)).show();*/
        DatePickerDialog mDate = new DatePickerDialog(CheckOutServiceActivity.this, date2, myCalendar2.get(Calendar.YEAR),  myCalendar2.get(Calendar.MONTH),  myCalendar2.get(Calendar.DAY_OF_MONTH));
        mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDate.show();



    }

    String applied_code_id=""; String applied_code_ ="";
    String applied_code_amount="";
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            applied_code_id = intent.getStringExtra("id");
            applied_code_ = intent.getStringExtra("code");
            applied_code_amount = intent.getStringExtra("amount");

            apply_promocode.setVisibility(View.GONE);
            promo_code_is_applied.setVisibility(View.VISIBLE);
            coupon_succesfully_applied.setText("Coupon "+applied_code_+" applied Successfully");
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

       // getTotalPayableAmount();
    }

    private Boolean _compare_wallet_to_netPayable(String current_wallet_balance, String total_payable_amount){
        double payableamount = Double.valueOf(total_payable_amount);
        double wallet_bal =  Double.valueOf(current_wallet_balance);

        if(payableamount>wallet_bal){
            LowBalanceBottomSheet fragment =new LowBalanceBottomSheet((payableamount-wallet_bal),wallet_bal);
            fragment.show(getSupportFragmentManager(), "TAG");
            return false;

        }
        return true;

    }

    private void getTotalPayableAmount(){
        progressDialog = new ProgressDialog(CheckOutServiceActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetTotalPayable> call = apiService.getPayableAmountFromServer("Bearer " + sessionManager.getKeySession(),sessionManager.getUserId(),mTotalCartAmount);
        call.enqueue(new Callback<GetTotalPayable>(){
            @Override
            public void onResponse(@NonNull Call<GetTotalPayable> call, @NonNull Response<GetTotalPayable> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()) {
                    current_wallet_balance = response.body().getCurrentWalletBalance();
                    current_total_reward_point = String.valueOf(response.body().getCurrentTotalRewardPoint());
                    Point_appleid = String.valueOf(response.body().getPointAppleid());
                    total_deduction = String.valueOf(response.body().getTotalDeduction());
                    total_payable_amount = String.valueOf(response.body().getTotalPayableAmount());
                    if (total_deduction.equals("0.00"))
                        mRewardContainer.setVisibility(View.GONE);
                    reward_checkbox.setText("You have reward points worth Rs ₹ "+total_deduction+" do you want to redem");

                }
            }
            @Override
            public void onFailure(Call <GetTotalPayable> call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }

    public UserDetailsResponse get_User_From_Shared_Prefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("user_info", "");


        return gson.fromJson(json, UserDetailsResponse.class);
    }

    UserDetailsResponse userDetailsResponse;
    private void init(){
        reward_checkbox.setText("You have reward points worth Rs ₹ ");
        checkout_btn.setText("Checkout ₹ "+mTotalCartAmount);
        radioGroup_select_env = (RadioGroup) findViewById(R.id.radio_grp_env);
        cod = (RadioButton) findViewById(R.id.cod);
        instantPayment = (RadioButton) findViewById(R.id.instantpayment);
        walletPay = (RadioButton) findViewById(R.id.wallet_pay);
        userDetailsResponse = get_User_From_Shared_Prefs(getApplicationContext());
        userDescription.setText(Html.fromHtml(productResponse.getDescription()));
        userName.setText(userDetailsResponse.getFirstName()+" "+userDetailsResponse.getLastName());
        userAddress.setText("Address : "+userDetailsResponse.getBilling().getAddress1()+" "+userDetailsResponse.getBilling().getAddress2()+" "+userDetailsResponse.getBilling().getCity()+" "+userDetailsResponse.getBilling().getState()+" "+userDetailsResponse.getBilling().getPostcode());
        mTitle.setText(productResponse.getName());
        mPrice.setText("Rs : "+productResponse.getPrice());

        Glide.with(getApplicationContext()).load(productResponse.getImages().get(0).getSrc())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(thumbnail);

    }

    @OnClick(R.id.layout_action1)
    public void  mEditLayout(View view){
        if (mCheckInternetWithMultipleClicks()) {
            Intent intent = new Intent(getApplicationContext(), UpdateUserDetails.class);
            startActivityForResult(intent,1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){


                fetchUserDetails();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult





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

    @OnClick(R.id.btn_signup)
    public void mBookService(View view){
        if (mBtn_flag){
           int radioButtonID = radioGroup_select_env.getCheckedRadioButtonId();
            if (radioButtonID == R.id.cod) {
                if (mCheckInternetWithMultipleClicks()) {
                    if(select_dates_check_box.isChecked()){

                        if(!first_date.getText().toString().matches("") && !second_date.getText().toString().matches("")) {
                            craeteScheduleOrder("cod");
                        }else {
                            Toast.makeText(getApplicationContext(), "Please select the date to Schedule the product" + second_date.getText(), Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        placedOrder("cod");
                    }

                }
            } else if (radioButtonID == R.id.instantpayment) {
                if (mCheckInternetWithMultipleClicks()) {
                    if(select_dates_check_box.isChecked()){

                        if(!first_date.getText().toString().matches("") && !second_date.getText().toString().matches("")) {
                            craeteScheduleOrder("paytm");
                        }else {
                            Toast.makeText(getApplicationContext(), "Please select the date to Schedule the product" + second_date.getText(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        placedOrder("paytm");
                    }


                }
            }else if (radioButtonID == R.id.wallet_pay){
                if (!isNullOrEmpty(current_wallet_balance) && !isNullOrEmpty(mTotalCartAmount))
                    if (_compare_wallet_to_netPayable(current_wallet_balance,mTotalCartAmount)){
                        /*book order from the wallet*/
                        if(select_dates_check_box.isChecked()){

                            if(!first_date.getText().toString().matches("") && !second_date.getText().toString().matches("")) {
                                craeteScheduleOrder("wallet");
                            }else {
                                Toast.makeText(getApplicationContext(), "Please select the date to Schedule the product" + second_date.getText(), Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            placedOrder("wallet");
                        }


                    }else {
                        Toast.makeText(getApplicationContext(),"internet connectivity is too low",Toast.LENGTH_SHORT).show();

                    }

            }
            else{
                Toast.makeText(getApplicationContext(),"select payment details",Toast.LENGTH_SHORT).show();

            }
        }else {
            mBtn_flag= true;
            mScriollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.title_check_out));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup =findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(CheckOutServiceActivity.this).inflate(R.layout.service_booked_dialog, viewGroup, false);
        Button btn = (Button) dialogView.findViewById(R.id.buttonOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NewCategoryActivity.class);
                startActivity(intent);
            }
        });

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutServiceActivity.this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    ProgressDialog progressDialog;

    private JSONObject createCodOrderRequest(){
        JSONObject jsonOrder = new JSONObject();
        try {
            jsonOrder.accumulate("payment_method", "COD");
            jsonOrder.accumulate("payment_method_title", "Cash on delivery");
            jsonOrder.accumulate("set_paid", "true");
            if (select_dates_check_box.isChecked()){
                jsonOrder.accumulate("start_date", first_date.getText().toString());
                jsonOrder.accumulate("end_date", second_date.getText().toString());

            }


            JSONArray jsonBilling = new JSONArray();
            JSONObject ob = new JSONObject();
            try {

                ob.accumulate("first_name",userDetailsResponse.getBilling().getFirstName());
                ob.accumulate("last_name",userDetailsResponse.getBilling().getLastName());
                ob.accumulate("address_1",userDetailsResponse.getBilling().getAddress1());
                ob.accumulate("address_2",userDetailsResponse.getBilling().getAddress2());
                ob.accumulate("city",userDetailsResponse.getBilling().getCity());
                ob.accumulate("state",userDetailsResponse.getBilling().getState());
                ob.accumulate("postcode",userDetailsResponse.getBilling().getPostcode());
                ob.accumulate("country",userDetailsResponse.getBilling().getCountry());
                ob.accumulate("email",userDetailsResponse.getBilling().getEmail());
                ob.accumulate("phone",userDetailsResponse.getBilling().getPhone());
                jsonBilling.put(ob);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            JSONObject ob1 = new JSONObject();
            try {

                ob1.accumulate("first_name",userDetailsResponse.getBilling().getFirstName());
                ob1.accumulate("last_name",userDetailsResponse.getBilling().getLastName());
                ob1.accumulate("address_1",userDetailsResponse.getBilling().getAddress1());
                ob1.accumulate("address_2",userDetailsResponse.getBilling().getAddress2());
                ob1.accumulate("city",userDetailsResponse.getBilling().getCity());
                ob1.accumulate("state",userDetailsResponse.getBilling().getState());
                ob1.accumulate("postcode",userDetailsResponse.getBilling().getPostcode());
                ob1.accumulate("country",userDetailsResponse.getBilling().getCountry());
                ob1.accumulate("email",userDetailsResponse.getBilling().getEmail());
                ob1.accumulate("phone",userDetailsResponse.getBilling().getPhone());
                // jsonShipping.put(ob1);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONArray shipping_lines = new JSONArray();
            try {
                JSONObject ob2 = new JSONObject();
                ob2.accumulate("method_id","flat_rate");
                ob2.accumulate("method_title","Flat Rate");
                ob2.accumulate("total","0");
                shipping_lines.put(ob2);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            jsonOrder.accumulate("billing",ob);
            jsonOrder.accumulate("customer_id", sessionManager.getUserId());
            jsonOrder.accumulate("shipping",ob1);
            jsonOrder.put("line_items",computeItemsList());
            if (!applied_code_.equals(""))
            jsonOrder.put("coupon_lines",computeAppliedCoupon());
            jsonOrder.put("shipping_lines",shipping_lines);




        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonOrder;
    }


    private JSONObject createPaytmOrderRerquest(){

        JSONObject jsonOrder = new JSONObject();
        try {
            jsonOrder.accumulate("payment_method", "paytm");
            jsonOrder.accumulate("payment_method_title", "Pay With Paytm");
            jsonOrder.accumulate("set_paid", false);



            JSONObject ob = new JSONObject();
            try {

                ob.accumulate("first_name",userDetailsResponse.getBilling().getFirstName());
                ob.accumulate("last_name",userDetailsResponse.getBilling().getLastName());
                ob.accumulate("address_1",userDetailsResponse.getBilling().getAddress1());
                ob.accumulate("address_2",userDetailsResponse.getBilling().getAddress2());
                ob.accumulate("city",userDetailsResponse.getBilling().getCity());
                ob.accumulate("state",userDetailsResponse.getBilling().getState());
                ob.accumulate("postcode",userDetailsResponse.getBilling().getPostcode());
                ob.accumulate("country",userDetailsResponse.getBilling().getCountry());
                ob.accumulate("email",userDetailsResponse.getBilling().getEmail());
                ob.accumulate("phone",userDetailsResponse.getBilling().getPhone());
                //    jsonBilling.put(ob);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            JSONObject ob1 = new JSONObject();
            try {

                ob1.accumulate("first_name",userDetailsResponse.getBilling().getFirstName());
                ob1.accumulate("last_name",userDetailsResponse.getBilling().getLastName());
                ob1.accumulate("address_1",userDetailsResponse.getBilling().getAddress1());
                ob1.accumulate("address_2",userDetailsResponse.getBilling().getAddress2());
                ob1.accumulate("city",userDetailsResponse.getBilling().getCity());
                ob1.accumulate("state",userDetailsResponse.getBilling().getState());
                ob1.accumulate("postcode",userDetailsResponse.getBilling().getPostcode());
                ob1.accumulate("country",userDetailsResponse.getBilling().getCountry());
                ob1.accumulate("email",userDetailsResponse.getBilling().getEmail());
                ob1.accumulate("phone",userDetailsResponse.getBilling().getPhone());
                // jsonShipping.put(ob1);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // computeItemsList();

            JSONArray shipping_lines = new JSONArray();
            try {
                JSONObject ob2 = new JSONObject();
                ob2.accumulate("method_id","flat_rate");
                ob2.accumulate("method_title","Flat Rate");
                ob2.accumulate("total","0");
                shipping_lines.put(ob2);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            jsonOrder.accumulate("billing",ob);
            jsonOrder.accumulate("customer_id", sessionManager.getUserId());
            jsonOrder.accumulate("shipping",ob1);
            jsonOrder.put("line_items",computeItemsList());
            if (!applied_code_.equals(""))
            jsonOrder.put("coupon_lines",computeAppliedCoupon());
            jsonOrder.put("shipping_lines",shipping_lines);




        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonOrder;
    }


    private JSONArray computeItemsList(){
        JSONArray line_items = new JSONArray();
        JSONObject ob1 = new JSONObject();
        JSONArray meta_data = new JSONArray();
        JSONObject ob_meta_data1 = new JSONObject();
        JSONObject ob_meta_data2 = new JSONObject();
            try {
                ob1.accumulate("product_id",productResponse.getId());


                try{
                    ob_meta_data1.accumulate("key","From");
                    ob_meta_data1.accumulate("value",mbookingDate);

                    ob_meta_data2.accumulate("key","To");
                    ob_meta_data2.accumulate("value",mbookingDate);

                }catch (Exception e){
                    e.printStackTrace();
                }
                meta_data.put(ob_meta_data1);
                meta_data.put(ob_meta_data2);
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }

        try {
            ob1.put("meta_data",meta_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        line_items.put(ob1);


        return line_items;

    }

    private JSONArray computeAppliedCoupon(){
        JSONArray coupon_items = new JSONArray();
        JSONObject ob1 = new JSONObject();
        try {

            ob1.accumulate("code",applied_code_);

            JSONArray meta_data = new JSONArray();
            JSONObject ob2 = new JSONObject();

            ob2.accumulate("key","coupon_data");

            JSONArray meta_data_value = new JSONArray();
            JSONObject ob3 = new JSONObject();
            ob3.accumulate("code",applied_code_);
            ob3.accumulate("id",applied_code_id);
            meta_data_value.put(ob3);

            ob2.accumulate("value",meta_data_value);

            meta_data.put(ob2);

            ob1.accumulate("meta_data",meta_data_value);



        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        coupon_items.put(ob1);

        return coupon_items;

    }

    private void fetchUserDetails(){


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserDetailsResponse> call = apiService.fetchUserDetails(sessionManager.getUserId(),"Bearer " + sessionManager.getKeySession());

        call.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                if(response.isSuccessful()){
                    storeUserInfoSharedPref(getApplicationContext(),response.body());

                    init();

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

    private void placedOrder(final String payment_mode){
        JSONObject orderRequest;
        switch (payment_mode) {
            case "cod":
                orderRequest = createCodOrderRequest();
                break;
            case "paytm":
                orderRequest = createOrderRerquest("paytm", "Pay With Paytm");
                break;
            default:
                orderRequest = createOrderRerquest("wallet", "Wallet payment");
                break;
        }
        progressDialog = new ProgressDialog(CheckOutServiceActivity.this);
        final String finalAmount="";
        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        RequestBody myreqbody = null;
        try {
            myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (new JSONObject(String.valueOf(orderRequest))).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<OrderResponse> call = apiService.createOrders("Bearer " + sessionManager.getKeySession(),myreqbody);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderResponse> call, @NonNull Response<OrderResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    if (payment_mode.equals("cod"))
                        showCustomDialog();
                    else {

                        if (!response.body().getCouponLines().isEmpty())
                        showCouponValueCustomDialog(response.body(), payment_mode);

                        else{
                            double netPyable = Double.valueOf(mTotalCartAmount);
                            if (payment_mode.equals("paytm")){
                                Intent intent = new Intent(getApplicationContext(), PaytmGatweyActivity.class);

                                if (reward_checkbox.isChecked()){
                                    double reward_applied = Double.valueOf(total_deduction);
                                    intent.putExtra("money", String.valueOf(netPyable- reward_applied));
                                }
                                else {
                                    intent.putExtra("money", String.valueOf(netPyable));
                                }
                                intent.putExtra("order_id",response.body().getId());
                                intent.putExtra("hasOrderId",true);
                                startActivity(intent);
                            }
                            else {
                                deductedWalletBalance(response.body().getId(),0);

                            }

                        }

                    }



                }else{
                    Log.e("Error","");
                    if (response.code() == 403){



                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // finish();
            }
        });
    }

    private void showCouponValueCustomDialog(final OrderResponse response, final String payment_mode) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(CheckOutServiceActivity.this).inflate(R.layout.custom_dialog_discount, viewGroup, false);
        Button btn = (Button) dialogView.findViewById(R.id.buttonOk);
        TextView mMessage = (TextView) dialogView.findViewById(R.id.message);
        mMessage.setText("Total discount balance "+response.getCouponLines().get(0).getDiscount());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double netPyable = Double.valueOf(mTotalCartAmount);
                double amount_after_couponApplied = Double.valueOf(response.getCouponLines().get(0).getDiscount());

                if (payment_mode.equals("paytm")){
                    Intent intent = new Intent(getApplicationContext(), PaytmGatweyActivity.class);

                    if (reward_checkbox.isChecked()){
                        double reward_applied = Double.valueOf(total_deduction);
                        intent.putExtra("money", String.valueOf(netPyable- reward_applied-amount_after_couponApplied));
                    }
                    else {
                        intent.putExtra("money", String.valueOf(netPyable - amount_after_couponApplied));
                         }
                    intent.putExtra("order_id",response.getId());
                    intent.putExtra("hasOrderId",true);
                    startActivity(intent);
                }
                else {
                    deductedWalletBalance(response.getId(),amount_after_couponApplied);

                }


            }
        });

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutServiceActivity.this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private JSONObject createOrderRerquest(String payment_method, String payment_method_title){

        JSONObject jsonOrder = new JSONObject();
        try {
            jsonOrder.accumulate("payment_method", payment_method);
            jsonOrder.accumulate("payment_method_title", payment_method_title);
            jsonOrder.accumulate("set_paid", false);
            if (select_dates_check_box.isChecked()){
                jsonOrder.accumulate("start_date", first_date.getText().toString());
                jsonOrder.accumulate("end_date", second_date.getText().toString());

            }


            JSONObject ob = new JSONObject();
            try {

                ob.accumulate("first_name",userDetailsResponse.getBilling().getFirstName());
                ob.accumulate("last_name",userDetailsResponse.getBilling().getLastName());
                ob.accumulate("address_1",userDetailsResponse.getBilling().getAddress1());
                ob.accumulate("address_2",userDetailsResponse.getBilling().getAddress2());
                ob.accumulate("city",userDetailsResponse.getBilling().getCity());
                ob.accumulate("state",userDetailsResponse.getBilling().getState());
                ob.accumulate("postcode",userDetailsResponse.getBilling().getPostcode());
                ob.accumulate("country",userDetailsResponse.getBilling().getCountry());
                ob.accumulate("email",userDetailsResponse.getBilling().getEmail());
                ob.accumulate("phone",userDetailsResponse.getBilling().getPhone());
                //    jsonBilling.put(ob);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            JSONObject ob1 = new JSONObject();
            try {

                ob1.accumulate("first_name",userDetailsResponse.getBilling().getFirstName());
                ob1.accumulate("last_name",userDetailsResponse.getBilling().getLastName());
                ob1.accumulate("address_1",userDetailsResponse.getBilling().getAddress1());
                ob1.accumulate("address_2",userDetailsResponse.getBilling().getAddress2());
                ob1.accumulate("city",userDetailsResponse.getBilling().getCity());
                ob1.accumulate("state",userDetailsResponse.getBilling().getState());
                ob1.accumulate("postcode",userDetailsResponse.getBilling().getPostcode());
                ob1.accumulate("country",userDetailsResponse.getBilling().getCountry());
                ob1.accumulate("email",userDetailsResponse.getBilling().getEmail());
                ob1.accumulate("phone",userDetailsResponse.getBilling().getPhone());
                // jsonShipping.put(ob1);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // computeItemsList();

            JSONArray shipping_lines = new JSONArray();
            try {
                JSONObject ob2 = new JSONObject();
                ob2.accumulate("method_id","flat_rate");
                ob2.accumulate("method_title","Flat Rate");
                ob2.accumulate("total","0");
                shipping_lines.put(ob2);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            jsonOrder.accumulate("billing",ob);
            jsonOrder.accumulate("customer_id", sessionManager.getUserId());
            jsonOrder.accumulate("shipping",ob1);
            jsonOrder.put("line_items",computeItemsList());
            if (!applied_code_.equals(""))
            jsonOrder.put("coupon_lines",computeAppliedCoupon());
            jsonOrder.put("shipping_lines",shipping_lines);




        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonOrder;
    }

    private void deductedWalletBalance(final Integer id, double amount_after_couponApplied){

        progressDialog = new ProgressDialog(this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Payment..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        WalletDeductReq walletDeductReq = new WalletDeductReq();

        double netPyable = Double.valueOf(mTotalCartAmount);
        if (reward_checkbox.isChecked()){
            double reward_applied = Double.valueOf(total_deduction);
            walletDeductReq.setAmount(String.valueOf(netPyable- reward_applied-amount_after_couponApplied));
        }
        else
            walletDeductReq.setAmount(String.valueOf(netPyable-amount_after_couponApplied));
        walletDeductReq.setType("debit");
        walletDeductReq.setDetails("For Payment of Order ");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WalletDeductedResponse> call = apiService.walletbalanceDeduct("Bearer " + sessionManager.getKeySession(),sessionManager.getUserId(),"debit",mTotalCartAmount,"For Payment of Order "+String.valueOf(id));

        call.enqueue(new Callback<WalletDeductedResponse>() {
            @Override
            public void onResponse(@NonNull Call<WalletDeductedResponse> call, @NonNull Response<WalletDeductedResponse> response) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if(response.isSuccessful()) {
                    // updatePlacedOrder(response.body().getId(),id);




                    if (response.body().getResponse().equals("success")){
                        Intent intent = new Intent(getApplicationContext(), UpdatePaymentDetailsActivity.class);
                        intent.putExtra("txid", response.body().getId());
                        intent.putExtra("order_id", id);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onFailure(Call<WalletDeductedResponse>  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }


    private void craeteScheduleOrder(final String payment_mode){
        /*JSONObject orderRequest;
        switch (payment_mode) {
            case "cod":
                orderRequest = createCodOrderRequest();
                break;
            case "paytm":
                orderRequest = createOrderRerquest("paytm", "Pay With Paytm");
                break;
            default:
                orderRequest = createOrderRerquest("wallet", "Wallet payment");
                break;
        }
        progressDialog = new ProgressDialog(CheckOutServiceActivity.this);
        final String finalAmount="";
        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        RequestBody myreqbody = null;
        try {
            myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (new JSONObject(String.valueOf(orderRequest))).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<OrderResponse> call = apiService.createScheduleOrders("Bearer " + sessionManager.getKeySession(),myreqbody);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderResponse> call, @NonNull Response<OrderResponse> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    if (payment_mode.equals("cod"))
                        showCustomDialog();
                    else {

                        if (!response.body().getMetaData().isEmpty())
                            showCouponValueCustomDialog(response.body(), payment_mode);

                        else{
                            double netPyable = Double.valueOf(mTotalCartAmount);
                            if (payment_mode.equals("paytm")){
                                Intent intent = new Intent(getApplicationContext(), PaytmGatweyActivity.class);

                                if (reward_checkbox.isChecked()){
                                    double reward_applied = Double.valueOf(total_deduction);
                                    intent.putExtra("money", String.valueOf(netPyable- reward_applied));
                                }
                                else {
                                    intent.putExtra("money", String.valueOf(netPyable));
                                }
                                intent.putExtra("order_id",response.body().getId());
                                intent.putExtra("hasOrderId",true);
                                startActivity(intent);
                            }
                            else {
                                deductedWalletBalance(response.body().getId(),0);

                            }

                        }

                    }



                }else{
                    Log.e("Error","");
                    if (response.code() == 403){



                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // finish();
            }
        });*/

    }

}
