package techlab.digital.com.ecommclap.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.fragments.ExpensesBottomSheet;
import techlab.digital.com.ecommclap.fragments.LoadMyOffersBottomSheet;
import techlab.digital.com.ecommclap.fragments.LowBalanceBottomSheet;
import techlab.digital.com.ecommclap.model.GetTotalPayable;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.walletDeduction.WalletDeductReq;
import techlab.digital.com.ecommclap.model.walletDeduction.WalletDeductedResponse;
import techlab.digital.com.ecommclap.model.cartModel.FetechCart.FetchCartResponse;
import techlab.digital.com.ecommclap.model.orderPlaced.OrderResponse;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class CheckoutProductActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.apply_promocode)
    TextView apply_promocode;
    SessionManager sessionManager;
    @BindView(R.id.scrollView)
    NestedScrollView mScriollView;
    ProductListingsModeResponse productResponse;
    @BindView(R.id.layout_action1)
    LinearLayout mEditLayout;
    @BindView(R.id.address)
    TextView userAddress;
    @BindView(R.id.name)
    TextView userBillingName;
    private RadioGroup radioGroup_select_env;
    Boolean mBtn_flag = false;
    String mbookingDate;
    RadioButton cod, instantPayment,walletPay;
    // variable to track event time`
    private long mLastClickTime = 0;
    TextView titleTextView;
    BottomSheetBehavior sheetBehavior;
    @BindView(R.id.btnAddExpense)
    Button ExpensesDetailsBtn;
    @BindView(R.id.promo_code_applied)
    LinearLayout promo_code_is_applied;
    @BindView(R.id.balance_container)
    LinearLayout order_money_layout;
    @BindView(R.id.mcheckBox)
    CheckBox reward_checkbox;
    @BindView(R.id.btn_signup)
    Button checkout_btn;
    @BindView(R.id.reward_continer)
            LinearLayout mRewardContainer;
    String wallet_bal,current_wallet_balance,current_total_reward_point,Point_appleid,total_deduction,total_payable_amount="";
    @BindView(R.id.coupon_apllied_success)
    TextView coupon_succesfully_applied;
    @BindView(R.id.remove_applied_coupon)
    TextView remove_promo_code;
    String applied_code_id=""; String applied_code_ ="";
    String applied_code_amount="";
    @BindView(R.id.first_date)
    TextView first_date;
    @BindView(R.id.second_date)
    TextView second_date;
    @BindView(R.id.dates_container)
    LinearLayout mdates_container;
    List<FetchCartResponse> mFinalTotal = new ArrayList<>();

    String mTotalCartAmount,shipping_total;
    ArrayList<FetchCartResponse> cart_items_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_product);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        final RadioGroup radio = (RadioGroup)findViewById(R.id.radio_grp_env);
        setToolBar();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        fetchIntentValue();
        getTotalPayableAmount();


      /*  apply_promocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadFragment(MyOffersFragments.newInstance());
                LoadMyOffersBottomSheet fragment_obj = new LoadMyOffersBottomSheet(getApplicationContext());
                *//*  fragment_obj.setCancelable(false);*//*
                fragment_obj.show(getSupportFragmentManager(), "TAG");


            }
        });*/
     /*   remove_promo_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promo_code_is_applied.setVisibility(View.GONE);
                apply_promocode.setVisibility(View.VISIBLE);

                applied_code_="";
                applied_code_amount="";
                applied_code_id="";
            }
        });*/


     /*   reward_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){

                    if (!isNullOrEmpty(total_deduction) ){
                        double netPyable = Double.valueOf(mTotalCartAmount);

                        double reward_applied = Double.valueOf(total_deduction);
                        total_payable_amount = String.valueOf(Double.valueOf(total_payable_amount) -  Double.valueOf(total_deduction));

                        checkout_btn.setText("Checkout ₹ "+(netPyable-reward_applied));
                    }

                } else {
                    total_payable_amount = String.valueOf(Double.valueOf(total_payable_amount) +  Double.valueOf(total_deduction));

                    checkout_btn.setText("Checkout ₹ "+mTotalCartAmount);

                }
            }
        });*/


        init();
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
                          //  Toast.makeText(getApplicationContext(),"internet connectivity is too low",Toast.LENGTH_SHORT).show();
                        }

                        break;
                }

            }
        });

        /*ExpensesDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpensesBottomSheet fragment = new ExpensesBottomSheet(current_wallet_balance,current_total_reward_point,Point_appleid,total_deduction,total_payable_amount,userDetailsResponse.getBilling().getAddress1()+" "+userDetailsResponse.getBilling().getAddress2()+" "+userDetailsResponse.getBilling().getCity()+" "+userDetailsResponse.getBilling().getState()+" "+userDetailsResponse.getBilling().getPostcode());
                fragment.show(getSupportFragmentManager(), "TAG");
            }
        });*/

    }


    @OnClick(R.id.apply_promocode)
    public void applyPromoCode(View view) {
        // TODO submit data to server...
        if (mCheckInternetWithMultipleClicks()) {

            //loadFragment(MyOffersFragments.newInstance());
            LoadMyOffersBottomSheet fragment_obj = new LoadMyOffersBottomSheet(getApplicationContext());
            /*  fragment_obj.setCancelable(false);*/
            fragment_obj.show(getSupportFragmentManager(), "TAG");
        }
    }


    @OnClick(R.id.remove_applied_coupon)
    public void removeAppliedPromoCode(View view) {
        // TODO submit data to server...
        if (mCheckInternetWithMultipleClicks()) {

            promo_code_is_applied.setVisibility(View.GONE);
            apply_promocode.setVisibility(View.VISIBLE);

            applied_code_="";
            applied_code_amount="";
            applied_code_id="";
        }
    }


    @OnClick(R.id.mcheckBox)
    public void mRewardCheckBoxClicked(View view) {
        // TODO submit data to server...
        if(((CompoundButton) view).isChecked()){

            if (!isNullOrEmpty(total_deduction) ){
                double netPyable = Double.valueOf(mTotalCartAmount);

                double reward_applied = Double.valueOf(total_deduction);
                total_payable_amount = String.valueOf(Double.valueOf(total_payable_amount) -  Double.valueOf(total_deduction));

                checkout_btn.setText("Checkout ₹ "+(netPyable-reward_applied));
            }

        } else {
            total_payable_amount = String.valueOf(Double.valueOf(total_payable_amount) +  Double.valueOf(total_deduction));

            checkout_btn.setText("Checkout ₹ "+mTotalCartAmount);

        }
    }


    @OnClick(R.id.btnAddExpense)
    public void mBtnExpenseClicked(View view) {
        // TODO submit data to server...

        if (mCheckInternetWithMultipleClicks()){
            ExpensesBottomSheet fragment = new ExpensesBottomSheet(current_wallet_balance,current_total_reward_point,Point_appleid,total_deduction,total_payable_amount,userDetailsResponse.getBilling().getAddress1()+" "+userDetailsResponse.getBilling().getAddress2()+" "+userDetailsResponse.getBilling().getCity()+" "+userDetailsResponse.getBilling().getState()+" "+userDetailsResponse.getBilling().getPostcode());
            fragment.show(getSupportFragmentManager(), "TAG");
        }
    }



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
           // Toast.makeText(CheckoutProductActivity.this,ItemName +" "+code ,Toast.LENGTH_SHORT).show();
        }
    };


    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

    private void fetchIntentValue(){

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cart_items_List  = extras.getParcelableArrayList("cart_items");
            mTotalCartAmount = extras.getString("cart_total");
            shipping_total = extras.getString("shipping_total");
        }


        mFinalTotal = cart_items_List;
        checkout_btn.setText("Checkout ₹ "+mTotalCartAmount);


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
        progressDialog = new ProgressDialog(CheckoutProductActivity.this);

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
        radioGroup_select_env = (RadioGroup) findViewById(R.id.radio_grp_env);
        cod = (RadioButton) findViewById(R.id.cod);
        instantPayment = (RadioButton) findViewById(R.id.instantpayment);
        walletPay = (RadioButton) findViewById(R.id.wallet_pay);
        userDetailsResponse = get_User_From_Shared_Prefs(getApplicationContext());
        if(!userDetailsResponse.equals(null))
        {
            try{
                userAddress.setText("Address : "+userDetailsResponse.getBilling().getAddress1()+" "+userDetailsResponse.getBilling().getAddress2()+" "+userDetailsResponse.getBilling().getCity()+" "+userDetailsResponse.getBilling().getPostcode());
                userBillingName.setText(userDetailsResponse.getBilling().getFirstName()+ " "+userDetailsResponse.getBilling().getLastName());
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        else {

        }
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
    }

    public void fetchCartProductsId(List<FetchCartResponse> mFinalCartList){

    }



 /* the below two methods are being used to fetch the data from the shared prefrence ior the fetch user details from the server*/
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

    private void storeUserInfoSharedPref(Context context, UserDetailsResponse finalContestModel){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(finalContestModel);
        prefsEditor.putString("user_info", json);
        prefsEditor.apply();
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

    @OnClick(R.id.btn_signup)
    public void mBookProduct(View view){
        if (mBtn_flag){
            int radioButtonID = radioGroup_select_env.getCheckedRadioButtonId();
            if (radioButtonID == R.id.cod) {
                if (mCheckInternetWithMultipleClicks()) {
                        placedOrder("cod");

                }
            } else if (radioButtonID == R.id.instantpayment) {
                Toast.makeText(getApplicationContext(),"Redirect to payment gateway",Toast.LENGTH_SHORT).show();
                if (mCheckInternetWithMultipleClicks()) {
                        placedOrder("paytm");
                }
            }
            else if (radioButtonID == R.id.wallet_pay){
                if (!isNullOrEmpty(current_wallet_balance) && !isNullOrEmpty(mTotalCartAmount))
                    if (_compare_wallet_to_netPayable(current_wallet_balance,mTotalCartAmount)){

                            placedOrder("wallet");

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


    /*------------- Below this all the given methdos are used to create an order of any type*/


    /*this is the master method to intialise the Order of any type*/
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
        progressDialog = new ProgressDialog(CheckoutProductActivity.this);

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
                    /* first we have to clear the cart
                        secondly we have to check the reward points is applied or not.., if reward is applied we have to update the update the reward point on the server
                           if all the above case are done than move to the Paytm Gateway

                    */

                    clearCartAfterOrderPlacedv2();
//                    check the reward point is applied or not
                    if (reward_checkbox.isChecked()){
                        updateUserRewardPoint();
                    }





                    if (payment_mode.equals("cod")){
                        /*redirect to the payments detail activity*/
                        Intent intent = new Intent(getApplicationContext(), UpdatePaymentDetailsActivity.class);
                        intent.putExtra("order_id", response.body().getId());
                        startActivity(intent);
                    }
                    else {

                        if (response.body() != null && response.body().getCouponLines().isEmpty()) {

                            checkingDeductingBalance(payment_mode,response.body());
                        }
                        else {
                            if (payment_mode.equals("paytm")) {
                                showCouponValueCustomDialog(response.body(),payment_mode);
                            }
                            else {
                                double amount_after_couponApplied = Double.valueOf(response.body().getCouponLines().get(0).getDiscount());
                                /*it means the payment tupe is wallet, so we are going to deduct the wallet balance*/
                                deductedWalletBalance(response.body().getId(),amount_after_couponApplied);

                            }

                        }
                    }



                }else{
                    // Timber.e(String.valueOf(response.code()));
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
        //before inflating the custom alert dialog highlight_remove, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(CheckoutProductActivity.this).inflate(R.layout.custom_dialog_discount, viewGroup, false);
        Button btn = (Button) dialogView.findViewById(R.id.buttonOk);
        TextView mMessage = (TextView) dialogView.findViewById(R.id.message);

        mMessage.setText("Total discount balance "+response.getCouponLines().get(0).getDiscount());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount_after_couponApplied = Double.valueOf(response.getCouponLines().get(0).getDiscount());

                if (payment_mode.equals("paytm")) {

                    settleMentJustBeforePaymentGateway(response.getId(),amount_after_couponApplied);
                }
                else {


                    deductedWalletBalance(response.getId(),amount_after_couponApplied);
                  //  settleMentJustBeforePaymentGateway("wallet",0, amount_after_couponApplied);
                }


            }
        });

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutProductActivity.this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private JSONArray computeItemsList(){
        JSONArray line_items = new JSONArray();
        JSONObject ob1 = null;


        for (FetchCartResponse mList : mFinalTotal){
           ob1 = new JSONObject();
            try {

                ob1.accumulate("product_id",mList.getProductId());
                ob1.accumulate("quantity",mList.getQuantity());
                ob1.accumulate("variation_id",mList.getVariationId());
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            line_items.put(ob1);

        }
        return line_items;

    }


    ProgressDialog progressDialog;
    private JSONObject createCodOrderRequest(){
        JSONObject jsonOrder = new JSONObject();
        try {
            jsonOrder.accumulate("payment_method", "COD");
            jsonOrder.accumulate("payment_method_title", "Cash on delivery");
            jsonOrder.accumulate("status", "processing");

            JSONArray shipping_lines = new JSONArray();
            try {
                JSONObject ob2 = new JSONObject();
                ob2.accumulate("method_id","flat_rate");
                ob2.accumulate("method_title","Flat Rate");
                ob2.accumulate("total",shipping_total);
                shipping_lines.put(ob2);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            jsonOrder.accumulate("billing",createBillingAddress());
            jsonOrder.accumulate("customer_id", sessionManager.getUserId());
            jsonOrder.accumulate("shipping",createShippingAddress());
            jsonOrder.put("line_items",computeItemsList());
            if (!applied_code_.equals(""))
            jsonOrder.put("coupon_lines",computeAppliedCoupon());
            if (reward_checkbox.isChecked())
                jsonOrder.put("fee_lines",computeFeeLine());
            jsonOrder.put("shipping_lines",shipping_lines);




        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonOrder;
    }

    /*this method create the order request of any type except the COD type orders*/
    private JSONObject createOrderRerquest(String payment_method, String payment_method_title){

        JSONObject jsonOrder = new JSONObject();
        try {
            jsonOrder.accumulate("payment_method", payment_method);
            jsonOrder.accumulate("payment_method_title", payment_method_title);
            jsonOrder.accumulate("set_paid", false);

            JSONArray shipping_lines = new JSONArray();
            try {
                JSONObject ob2 = new JSONObject();
                ob2.accumulate("method_id","flat_rate");
                ob2.accumulate("method_title","Flat Rate");
                ob2.accumulate("total",shipping_total);
                shipping_lines.put(ob2);

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            jsonOrder.accumulate("billing",createBillingAddress());
            jsonOrder.accumulate("customer_id", sessionManager.getUserId());
            jsonOrder.accumulate("shipping",createShippingAddress());
            jsonOrder.put("line_items",computeItemsList());
            if (!applied_code_.equals(""))
            jsonOrder.put("coupon_lines",computeAppliedCoupon());
            if (reward_checkbox.isChecked())
                jsonOrder.put("fee_lines",computeFeeLine());
            jsonOrder.put("shipping_lines",shipping_lines);




        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonOrder;
    }


    /*create a shipping address JSon */
    private JSONObject createShippingAddress(){
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

        return  ob1;

    }

    /*create a billing addrress json*/
    private JSONObject createBillingAddress(){
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
          //  jsonBilling.put(ob);

        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ob;
    }


    /*this method conpoute the Applied coupon*/
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

    /*if the reward point is applied, than we have to calculate the feeline items else there is no need to send tbe feeLine items to the server*/
    private JSONArray computeFeeLine(){

        double reward_applied = 0-Double.valueOf(total_deduction);
        JSONArray fee_lines_items = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("name", "Via mycred");
            jsonObject.accumulate("amount", String.valueOf(reward_applied));
            jsonObject.accumulate("total", String.valueOf(reward_applied));
        }catch (JSONException e){
            e.printStackTrace();
        }
            fee_lines_items.put(jsonObject);
        return fee_lines_items;
    }

    /*it helps to reduce the wallet balance*/
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

    private void checkingDeductingBalance(String payment_mode, OrderResponse body){
        if (payment_mode.equals("paytm")) {
            settleMentJustBeforePaymentGateway( body.getId(),0);
        }
        else {

            deductedWalletBalance(body.getId(),0);
        }


    }


    private void clearCartAfterOrderPlacedv2(){

        progressDialog = new ProgressDialog(CheckoutProductActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Redirect..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call call = apiService.cartClear("Bearer " + sessionManager.getKeySession());
        // Log.e("token********",sessionManager.getKeySession());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

               /* if(response.isSuccessful()) {

                    if (flow.equals("paytm")){
                        double netPyable = Double.valueOf(mTotalCartAmount);
                        Intent intent = new Intent(getApplicationContext(), PaytmGatweyActivity.class);
                        if (reward_checkbox.isChecked()){
                            // double netPyable = Double.valueOf(mTotalCartAmount);
                            double reward_applied = Double.valueOf(total_deduction);
                            intent.putExtra("money", String.valueOf(netPyable- reward_applied-amount_after_couponApplied));
                        }
                        else
                            intent.putExtra("money", String.valueOf(netPyable - amount_after_couponApplied));
                        intent.putExtra("order_id",order_id);
                        intent.putExtra("hasOrderId",true);
                        startActivity(intent);
                    }
                    else if (flow.equals("wallet")){

                    }
                    else {

                        Intent intent = new Intent(getApplicationContext(), UpdatePaymentDetailsActivity.class);

                        intent.putExtra("order_id", order_id);
                        startActivity(intent);
                    }
                }*/
            }

            @Override
            public void onFailure(Call  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }

    /*it helps to create a cart after order is being placed*/
    private void settleMentJustBeforePaymentGateway( final int order_id, final double amount_after_couponApplied){



                        double netPyable = Double.valueOf(mTotalCartAmount);
                        Intent intent = new Intent(getApplicationContext(), PaytmGatweyActivity.class);
                        if (reward_checkbox.isChecked()){
                            double reward_applied = Double.valueOf(total_deduction);
                            intent.putExtra("money", String.valueOf(netPyable- reward_applied-amount_after_couponApplied));
                        }
                        else
                            intent.putExtra("money", String.valueOf(netPyable - amount_after_couponApplied));
                        intent.putExtra("order_id",order_id);
                        intent.putExtra("hasOrderId",true);
                        startActivity(intent);





    }

    /*update user reward points*/
    private void updateUserRewardPoint(){

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call call = apiService.userRewardPointDeduct("Bearer " + sessionManager.getKeySession(),sessionManager.getUserId(),total_deduction);
        // Log.e("token********",sessionManager.getKeySession());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
/*

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
*/

               /* if(response.isSuccessful()) {

                    if (flow.equals("paytm")){
                        double netPyable = Double.valueOf(mTotalCartAmount);
                        Intent intent = new Intent(getApplicationContext(), PaytmGatweyActivity.class);
                        if (reward_checkbox.isChecked()){
                            // double netPyable = Double.valueOf(mTotalCartAmount);
                            double reward_applied = Double.valueOf(total_deduction);
                            intent.putExtra("money", String.valueOf(netPyable- reward_applied-amount_after_couponApplied));
                        }
                        else
                            intent.putExtra("money", String.valueOf(netPyable - amount_after_couponApplied));
                        intent.putExtra("order_id",order_id);
                        intent.putExtra("hasOrderId",true);
                        startActivity(intent);
                    }
                    else if (flow.equals("wallet")){

                    }
                    else {

                        Intent intent = new Intent(getApplicationContext(), UpdatePaymentDetailsActivity.class);

                        intent.putExtra("order_id", order_id);
                        startActivity(intent);
                    }
                }*/
            }

            @Override
            public void onFailure(Call  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());

              /*  if (progressDialog.isShowing())
                    progressDialog.dismiss();*/

            }
        });
    }


}