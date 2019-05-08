package techlab.digital.com.ecommclap.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartReq;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ServiceDescriptionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ProductListingsModeResponse productResponse;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.product_title)
    TextView mMainTitle;
    @BindView(R.id.product_subTitle)
    TextView mMainSubTitle;
    @BindView(R.id.serviceCost)
    TextView mBookingPrice;
    @BindView(R.id.booking_dateView)
    TextView mBookingDate;
    @BindView(R.id.cardView8)
    CardView mBookingDateView;
    @BindView(R.id.datePicker)
    TextView mBookingDateTextView;
SessionManager sessionManager;
    Bundle bundle, savedInstanceState;
    @BindView(R.id.btn_signup)
    Button mBookService;

    // variable to track event time`
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        Intent intent = getIntent();

        if (null != intent.getExtras()) {

            productResponse = getIntent().getExtras().getParcelable("object");
            init();
        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();
        initViews();




    }

    private void initViews(){
        if (mCheckInternetWithMultipleClicks()) {
            try {
                Glide.with(this).load(productResponse.getImages().get(0).getSrc()).into((ImageView) findViewById(R.id.backdrop));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick(R.id.btn_signup)
    public void mBookService(View view){
        if (sessionManager.isLoggedIn()){

    if (selectedDate.equals("")){
        Toast.makeText(getApplicationContext(),"provide service date",Toast.LENGTH_SHORT).show();

    }
    else {
        if (mCheckInternetWithMultipleClicks()) {



            /* here we have to add the service into the cart*/


            redirectTocheckOutPage();
        }
    }
        }else {
            sessionManager.checkLogin();
        }


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

   Boolean isDateSet = false;
    @OnClick(R.id.cardView8)
    public void setServiceDate(){

        createCustomDialog(savedInstanceState);
    }

    private void init(){
        mDescription.setText(Html.fromHtml(productResponse.getDescription()));
        mMainTitle.setText(productResponse.getName());
        mBookingPrice.setText("Service Cost - Rs : "+productResponse.getPrice());

    }

    int yy, mm, dd;
    public void createCustomDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
                yy = calendar.get(Calendar.YEAR);
                mm = calendar.get(Calendar.MONTH);
                dd = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ServiceDescriptionActivity.this, R.style.datepickerCustom, this, yy, mm, dd);
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.setTitle("Select Date");
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            datePickerDialog.show();

    }

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

    int temp_hour;
    String myValue, selectedDate="", selectedDateFormatted;
    public void populateTime(int hour, int min) {
        if (hour >=1 && hour < 8){
            temp_hour = hour;
            temp_hour += 12;
        }

        selectedDateFormatted = selectedDate + " " + temp_hour + ":" + min + ":" + 00;
        if (hour == 12 || hour < 8) {
            //   int i = hour - 12;
            selectedDate = selectedDate + " " + checkDigit(hour) + ":" + checkDigit(min) + "  " + "PM";
        } else {
            selectedDate = selectedDate + " " + checkDigit(hour) + ":" + checkDigit(min)  + "  " + "AM";
        }

        String next = getResources().getString(R.string.sub_booking_date);
        mBookingDate.setVisibility(View.VISIBLE);
        mBookingDate.setText(Html.fromHtml(next) + selectedDate);
        mBookingDate.setTextColor(Color.rgb(255, 193, 7));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
       /* try {
            serviceDate = formatter.parse(selectedDateFormatted);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
      //  mCallback.onDateSelected(serviceDate);
    }


    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(productResponse.getName());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    String passingDate ="";
    public void populateSetDate(int year, int month, int day) {
        selectedDate = checkDigit(day)+"-"+checkDigit(month+1)+"-"+year;
        passingDate = year+"-"+checkDigit(month+1)+"-"+checkDigit(day);
        String next = getResources().getString(R.string.sub_booking_date);
        mBookingDate.setVisibility(View.VISIBLE);
        mBookingDate.setText(Html.fromHtml(next) + selectedDate);
        mBookingDate.setTextColor(Color.rgb(255, 193, 7));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        populateSetDate(i, i1 , i2);

    }


    private void redirectTocheckOutPage(){
        Intent intent = new Intent(getApplicationContext(),CheckOutServiceActivity.class);
        intent.putExtra("service_date",passingDate);
        intent.putExtra("object", productResponse);
        startActivity(intent);

    }




}
