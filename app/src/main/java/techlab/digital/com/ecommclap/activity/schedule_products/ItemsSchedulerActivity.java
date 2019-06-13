package techlab.digital.com.ecommclap.activity.schedule_products;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.UpdatePaymentDetailsActivity;
import techlab.digital.com.ecommclap.model.ScheduleOrderResponse.ScheduleOrdrResp;
import techlab.digital.com.ecommclap.model.VirtualCartSingleton;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponsetTwo;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ItemsSchedulerActivity extends AppCompatActivity {
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    String type;
    Button schedular;
    private long mLastClickTime = 0;
    List<ProductListingsModeResponsetTwo> final_cart_item = new ArrayList<>();
    VirtualCartSingleton tmp = VirtualCartSingleton.getInstance( );
    List<ProductListingsModeResponsetTwo> mfinalList=new ArrayList<>();
    SessionManager sessionManager;
    VirtualCartSingleton temp = VirtualCartSingleton.getInstance( );
    String day;
    String[] daysList = new String[]{
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_scheduler);
        setToolBar();
        schedular=findViewById(R.id.schedular_product);
        sessionManager = new SessionManager(getApplicationContext());
        checkPlaceOrderList();

        final_cart_item=temp.getmFinalList_for_cart();


        checkBox1 = findViewById(R.id.first_check);
        checkBox2 = findViewById(R.id.second_check);
        checkBox3 = findViewById(R.id.third_check);
        checkBox4 = findViewById(R.id.four_check);

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox1.setChecked(true);
                //  scheduleProduct("daily");
                type="daily";
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
            }
        });


        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox2.setChecked(true);
                //   scheduleProduct("daily_exclude_weekends");
                type="daily_exclude_weekends";
                checkBox1.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox3.setChecked(true);
                //  scheduleProduct("weekly");
                type="weekly";
                createDialogForDays();
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox4.setChecked(false);
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox4.setChecked(true);
                //     scheduleProduct("bi-weekly");

                createDialogForDays();
                type="bi-weekly";
                checkBox1.setChecked(false);
                checkBox3.setChecked(false);
                checkBox2.setChecked(false);
            }
        });


        schedular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckInternetWithMultipleClicks()){

                    scheduleProduct(type);
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


    private void createDialogForDays(){

        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(ItemsSchedulerActivity.this);
        alertdialogbuilder.setCancelable(false);

        alertdialogbuilder.setTitle("Select a days");

        alertdialogbuilder.setItems(daysList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedText = Arrays.asList(daysList).get(which);

                //  textview.setText(selectedText);


                day= selectedText.substring(0,3);

                Toast.makeText(getApplicationContext(),selectedText,Toast.LENGTH_SHORT).show();



            }
        });

        AlertDialog dialog = alertdialogbuilder.create();

        dialog.show();


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

    public void scheduleProduct(String type)
    {
        if(!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked()){

            Toast.makeText(getApplicationContext(), "Plz Select the Scheduling Method", Toast.LENGTH_SHORT).show();
        }
        else{
            craeteScheduleOrder(type);   //daily
        }

    }

    Calendar myCalendar = Calendar.getInstance();
    UserDetailsResponse userDetailsResponse;
    private JSONObject createOrderRerquest(String type){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        userDetailsResponse = get_User_From_Shared_Prefs(getApplicationContext());
        JSONObject jsonOrder = new JSONObject();
        try {
            JSONArray jsonBilling = new JSONArray();
            JSONObject ob = new JSONObject();   //BILLING.........
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


            jsonOrder.accumulate("start_date", sdf.format(myCalendar.getTime()));
            jsonOrder.accumulate("end_date", "");
            jsonOrder.accumulate("billing", ob);
            jsonOrder.accumulate("customer_id", sessionManager.getUserId());
            jsonOrder.accumulate("type", type);
            if(type.equals("bi-weekly") || type.equals("weekly")){

                jsonOrder.accumulate("day", day);
            }
            jsonOrder.accumulate("shipping", ob1);
            jsonOrder.put("line_items", computeItemsList());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonOrder;
    }

    public UserDetailsResponse get_User_From_Shared_Prefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("user_info", "");


        return gson.fromJson(json, UserDetailsResponse.class);
    }

    ProgressDialog progressDialog;

    private void craeteScheduleOrder(String type){
        JSONObject orderRequest = createOrderRerquest(type);

        progressDialog = new ProgressDialog(ItemsSchedulerActivity.this);
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

        Call<ScheduleOrdrResp> call = apiService.createScheduleOrder("Bearer " + sessionManager.getKeySession(),myreqbody);

        call.enqueue(new Callback<ScheduleOrdrResp>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleOrdrResp> call, @NonNull Response<ScheduleOrdrResp> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    Log.e("response", String.valueOf(response.body()));

                    showCustomDialog(response.body().getId());

                }else{
                    Log.e("Error","");
                    if (response.code() == 403){
                    }
                }
            }
            @Override
            public void onFailure(Call<ScheduleOrdrResp> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }

    private JSONArray computeItemsList(){
        JSONArray line_items = new JSONArray();
        JSONObject ob1 = null;
        for (ProductListingsModeResponsetTwo mList : final_cart_item){
            ob1 = new JSONObject();
            try {
                ob1.accumulate("product_id",mList.getId());
                ob1.accumulate("quantity",mList.getQuantity());
                //  ob1.accumulate("variation_id",mList.getVariations());
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

    public void checkPlaceOrderList() {
        mfinalList=tmp.getmFinalList_for_cart();
        if (mfinalList.size() != 0) {
            schedular.setVisibility(View.VISIBLE);
        }
        else {
            schedular.setVisibility(View.GONE);
        }
    }

    private void showCustomDialog(final Integer id) {
        //before inflating the custom alert dialog highlight_remove, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(ItemsSchedulerActivity.this).inflate(R.layout.service_booked_dialog, viewGroup, false);
        Button btn = (Button) dialogView.findViewById(R.id.buttonOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SET THE SINGLETON  LIST NULL AND SEND TO USERpROFILEaCTIVITY.......
                temp.setmFinalList_for_cart(null);

                Intent intent = new Intent(getApplicationContext(), UpdatePaymentDetailsActivity.class);

                intent.putExtra("order_id", id);
                startActivity(intent);
            }
        });
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemsSchedulerActivity.this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
