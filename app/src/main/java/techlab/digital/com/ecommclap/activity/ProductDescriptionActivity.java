package techlab.digital.com.ecommclap.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartReq;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartResponse;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartWithVariationReq;
import techlab.digital.com.ecommclap.model.fetchSubProducts.CustomVariations;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductVariationContainer;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ProductDescriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ProductListingsModeResponse productResponse;
    ProductVariationContainer productVariationContainer;
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

    @BindView(R.id.variations)
    Spinner mVariations;
    SessionManager sessionManager;

    @BindView(R.id.addTocart)
    Button mAddToCart;

    Bundle bundle, savedInstanceState;
    // variable to track event time`
    private long mLastClickTime = 0;
    @BindView(R.id.quantity1)
    ElegantNumberButton mQuantity;

    ArrayList<CustomVariations> mVariationsIdList = new ArrayList<>();;
    ArrayList<String> arrayList;
    String temp_price,temp_Quantity_Size,temp_variations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        Intent intent = getIntent();

        if (null != intent.getExtras()) {

            productResponse = getIntent().getExtras().getParcelable("object");
            productVariationContainer = getIntent().getExtras().getParcelable("variation");
            paths= productVariationContainer.getQuantity_attributes();
            init();
        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (productResponse.getType().equals("variable"))
        checkvariations();
        else{
            /*it means there si no varitaion*/

            mVariations.setVisibility(View.GONE);



        }

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

    private void checkvariations(){
        if (!productResponse.getAttributes().isEmpty())

        initializeUIForVariations();
        else
            mVariations.setVisibility(View.GONE);

    }

    @OnClick(R.id.addTocart)
    public void addToCart(View view){


        if (sessionManager.isLoggedIn()){
            if (mCheckInternetWithMultipleClicks()) {
                addtoCartProduct();
            }
        }else {

            sessionManager.checkLogin();
        }


    }
    ProgressDialog progressDialog;
    private void addtoCartProduct(){

        Call<AddToCartResponse> call = null;
        progressDialog = new ProgressDialog(ProductDescriptionActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        if (productResponse.getType().equals("variable")) {


            AddToCartWithVariationReq addToCartWithReq = new AddToCartWithVariationReq(String.valueOf(productResponse.getId()),temp_variations,String.valueOf(mQuantity.getNumber()));
               call = apiService.addToCart("Bearer " + sessionManager.getKeySession(),addToCartWithReq);


        }
        else{

         AddToCartReq   addToCartReq = new AddToCartReq(String.valueOf(productResponse.getId()),String.valueOf(mQuantity.getNumber()));
             call = apiService.addToCart("Bearer " + sessionManager.getKeySession(),addToCartReq);
        }


        call.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    showCustomDialog();

                }else{
                    Log.e("Error","");
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                //noResults.setVisibility(View.VISIBLE);

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });

    }

    private void init(){
        mDescription.setText(Html.fromHtml(productResponse.getDescription()));
        mMainTitle.setText(productResponse.getName());
        mBookingPrice.setText("Price : "+productResponse.getPrice() + " Rs ");

    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup =findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(ProductDescriptionActivity.this).inflate(R.layout.my_dialog, viewGroup, false);
        Button btn = (Button) dialogView.findViewById(R.id.buttonOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NewCategoryActivity.class);
                startActivity(intent);
            }
        });

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDescriptionActivity.this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    private static HashMap<String,CustomVariations> paths;

    private void initializeUIForVariations() {
        arrayList = new ArrayList<>();
        for (Map.Entry<String, CustomVariations> entry : paths.entrySet()) {
            System.out.println("Dhruv.. "+entry.getKey() + " = " + entry.getValue());
            //   arrayList = new ArrayList<>();
            arrayList.add(entry.getValue().getOption());
        }
        temp_price = productResponse.getPrice();
        temp_Quantity_Size = arrayList.get(0).toString();
        temp_variations = String.valueOf(paths.entrySet().iterator().next().getValue().getId());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mVariations.setAdapter(adapter);
        mVariations.setOnItemSelectedListener(this);

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

    int selectedVariationId=0;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String selectedItemTitle = mVariations.getSelectedItem().toString();
        for (Map.Entry<String, CustomVariations> entry : paths.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());

            if (selectedItemTitle.equals(entry.getValue().getOption())){
              //  itemPrice.setText("Rs."+entry.getKey());
                mBookingPrice.setText("Price : "+entry.getKey() + " Rs ");

                temp_price = entry.getKey();

                temp_variations = String.valueOf(entry.getValue().getId());
                temp_Quantity_Size = entry.getValue().getOption();
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
