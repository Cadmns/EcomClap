package techlab.digital.com.ecommclap.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.ProductListingsAdapter;
import techlab.digital.com.ecommclap.adapter.SubCategoriesAdapter;
import techlab.digital.com.ecommclap.fragments.AddNewOrRepeatBottomSheet;
import techlab.digital.com.ecommclap.fragments.BookServiceBottomSheet;
import techlab.digital.com.ecommclap.fragments.ProductVariationsSheet;
import techlab.digital.com.ecommclap.fragments.UpdateQuantityOnlySheet;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartReq;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartResponse;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartWithVariationReq;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.NewAddTocart;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.newAddToCartResponse;
import techlab.digital.com.ecommclap.model.categories.subCategories.FetchSubCategory;
import techlab.digital.com.ecommclap.model.fetchSubProducts.CustomVariations;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductVariationContainer;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ProductListings extends AppCompatActivity implements ProductListingsAdapter.OnAddProductButtonListener,ProductListingsAdapter.OnInterfaceListener2,ProductListingsAdapter.OnServiceBooked,
                                            ProductVariationsSheet.onVariationChanged,UpdateQuantityOnlySheet.onQuantityUpdateOnly,
                                            AddNewOrRepeatBottomSheet.onOptionSelected,BookServiceBottomSheet.onDateSelected{
    int product_id;
    String category_name;
    @BindView(R.id.toolbarTitle)
    TextView mToolBarName;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.noResults)
    TextView noResults;
    String p_variation,p_quantity;
    SessionManager sessionManager;
    //variable to track event time`
    private long mLastClickTime = 0;
    //AddToCartResponse addToCartResponse;

    newAddToCartResponse addToCartResponse;
    List<ProductListingsModeResponse> product_data_List;
    ProductListingsModeResponse productListingsModeResponse2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listings);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        Intent intent = getIntent();
        if (null != intent.getExtras()) {
            product_id =getIntent().getExtras().getInt("id");
            category_name =getIntent().getExtras().getString("category_name");
            settoolbar();
            if (CheckInternet.isNetwork(getApplicationContext())) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                fetchSubCategories(product_id);
                  //  fetchproducts(category_name);
            }else {
                //do something, net is not connected
                Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void settoolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolBarName = toolbar.findViewById(R.id.toolbarTitle);
        mToolBarName.setText(category_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    ProgressDialog progressDialog;



    //Setting up Progress dialog for loading the content.
    private void fetchproducts(String str){
        try {
            progressDialog = new ProgressDialog(ProductListings.this);
            progressDialog.setMessage("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }
                catch (WindowManager.BadTokenException e) {
            //use a log message
        }
       /* if (progressDialog != null){
            progressDialog.show();
        }*/
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductListingsModeResponse>> call = apiService.getProducts(sessionManager.getUserCity(),str);
        call.enqueue(new Callback<List<ProductListingsModeResponse>>() {
            @Override
            public void onResponse(Call<List<ProductListingsModeResponse>> call, Response<List<ProductListingsModeResponse>> response) {

                   /* if (progressDialog!=null)
                    {
                        if (progressDialog.isShowing())
                        {
                            progressDialog.dismiss();   //This is line 624!
                        }
                    }*/
                try {
                    if ((progressDialog != null) && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                }

                if(response.isSuccessful()){

                        setAdapterViews(response.body());


                }else{
                    Log.e("Error","");
                }
            }
            @Override
            public void onFailure(Call<List<ProductListingsModeResponse>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                try {
                    if ((progressDialog != null) && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                }
            }
        });
    }


    //fetch subcategories.....

    private void fetchSubCategories(final int str){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<FetchSubCategory>> call = apiService.getSubcategories(str);
        call.enqueue(new Callback<List<FetchSubCategory>>() {
            @Override
            public void onResponse(Call<List<FetchSubCategory>> call, Response<List<FetchSubCategory>> response) {

                if(response.isSuccessful()){
                    if(response.body().isEmpty())
                    {
                        fetchproducts(category_name);
                    }
                    else {
                        createAdapter(response.body());
                    }
                }else{
                    Log.e("Error","");
                  //  mNoResults.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<FetchSubCategory>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());


            }
        });
    }
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter subCatAdapter;
    //set subcatogories adapter.....
    private void createAdapter(List<FetchSubCategory> body){

       /* if(body.isEmpty())
        {
            fetchproducts(ImageListFragment.this.getArguments().getString("cat_slug"));

        }*/
        recyclerView= findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        // recyclerView.setHasFixedSize(true);
        subCatAdapter = new SubCategoriesAdapter(ProductListings.this, body);
        recyclerView.setAdapter(subCatAdapter);
        /*mEtaContainer.setVisibility(View.VISIBLE);
        mCategoryEta.setText(ProductListings.this.getArguments().getString("eta"));*/

    }
    ProductListingsAdapter mAdapter;
    private void setAdapterViews(List<ProductListingsModeResponse> datumList){
        product_data_List = datumList;
        mAdapter = new ProductListingsAdapter(ProductListings.this, datumList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.mCallback = (ProductListingsAdapter.OnAddProductButtonListener)this;
        mAdapter.mCallback2 = (ProductListingsAdapter.OnInterfaceListener2)this;
        mAdapter.mCallback3 = (ProductListingsAdapter.OnServiceBooked)this;
    }


    /***************Interface CallBack to Add to cart the Product on add button click*******************/
    @Override
    public void OnAddProduct(View view, int position, ProductListingsModeResponse object, String quantity, ProductListingsAdapter.ViewHolder mholder) {
            ProductListingsModeResponse  product_object_data = object;
            ProductVariationsSheet frgaments = new ProductVariationsSheet(getApplicationContext(),product_object_data,position,quantity,mholder);
            frgaments.show(getSupportFragmentManager(), "ahahahaha");
            frgaments.setCancelable(false);
    }

    /*interface call back chose add new product or update last one*/
    @Override
    public void OnAddOrRepeat(View view, int position, ProductListingsModeResponse m_data, String quantity, ProductListingsAdapter.ViewHolder holder) {
        AddNewOrRepeatBottomSheet fragmentEliminaPost = new AddNewOrRepeatBottomSheet(getApplicationContext(),m_data,position,quantity,holder);
        fragmentEliminaPost.show(getSupportFragmentManager(), "ahahahaha");
        fragmentEliminaPost.setCancelable(false);
    }

    /*Interface caLLBack to get User Choice wheather he wants to add new product or repeat last one*/
    @Override
    public void selected_option_is(String   option, ProductListingsModeResponse m_data_object, int data_position, String number_quantity, ProductListingsAdapter.ViewHolder holder) {
        if(option.equals("add"))
        {
            try {
                ProductVariationContainer m_variation_container = is_having_variations(m_data_object);
                if(!m_variation_container.equals(null))
                {
                    ProductVariationsSheet fragmentEliminaPost = new ProductVariationsSheet(getApplicationContext(),m_data_object,data_position,number_quantity,holder);
                    fragmentEliminaPost.show(getSupportFragmentManager(), "ahahahaha");
                    fragmentEliminaPost.setCancelable(false);
                }
                else
                    Log.e("TAG","no variation is provided");
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

        }
        else if(option.equals("repeat")){
            UpdateQuantityOnlySheet fragmentEliminaPost = new UpdateQuantityOnlySheet(getApplicationContext(),m_data_object,number_quantity,data_position,holder);
            fragmentEliminaPost.show(getSupportFragmentManager(), "ahahahaha");
            fragmentEliminaPost.setCancelable(false);
        }

    }

    /*this method is used to update the quantity and variations of the product*/
    @Override
    public void change_variation_is(String variation, String quantity, ProductListingsModeResponse add_cart_product_response, int product_position, ProductListingsAdapter.ViewHolder holder) {
        int updateIndex = product_position;
        mAdapter.update_elegent(updateIndex,quantity,holder);
        p_variation = variation;
        p_quantity = quantity;

        Log.e("here is the issue",p_variation+"_______"+p_quantity);
        addtoCartProduct(add_cart_product_response,variation,quantity);
    }

    /*interface call back to update the quantity only */
    @Override
    public void update_quantity_only(String quantity, ProductListingsModeResponse object_data, int position, ProductListingsAdapter.ViewHolder my_holder) {
        mAdapter.update_elegent(position,quantity,my_holder);
        updateQuantity(object_data,quantity);
        Toast.makeText(getApplicationContext(),quantity,Toast.LENGTH_SHORT).show();
    }

    /*interface call back to get service date from bottom sheet*/
    @Override
    public void onbookService(ProductListingsModeResponse data) {
        productListingsModeResponse2 = new ProductListingsModeResponse();
        productListingsModeResponse2 = data;
        BookServiceBottomSheet fragmentEliminaPost = new BookServiceBottomSheet(getApplicationContext());
        fragmentEliminaPost.show(getSupportFragmentManager(), "ahahahaha");
    }

    @Override
    public void selected_date_is(String date) {
        Toast.makeText(getApplicationContext(),"date is"+date,Toast.LENGTH_SHORT).show();
        if (sessionManager.isLoggedIn()){
            Intent intent = new Intent(getApplicationContext(),CheckOutServiceActivity.class);
            intent.putExtra("service_date",date);
            intent.putExtra("object", productListingsModeResponse2);
            startActivity(intent);
        }
        else {
            sessionManager.checkLogin();
        }
    }

    /*network calling to update the quantiity*/
    private void updateQuantity(ProductListingsModeResponse response,String quantity){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.updateQuantity("Bearer " + sessionManager.getKeySession(),response.getmRefrenceKey(),quantity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Item Updated Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("Error","");
                    if (response.code() == 403){
                        /*do call back  for forbidden erorr*/
                    }
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

            }
        });


    }

    /*network calling to add to cart to server and app ass well*/
    private void addtoCartProduct(final ProductListingsModeResponse product_data_holder,String selected_variation,String selected_quantity){
        Call<newAddToCartResponse> call = null;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        AddToCartReq addToCartReq = new AddToCartReq(String.valueOf(product_data_holder.getId()),selected_quantity);

     /*   NewAddTocart addToCartReq = new NewAddTocart();
        addToCartReq.setProductId(String.valueOf(product_data_holder.getId()));
        addToCartReq.setQuantity(selected_quantity);
        Log.e("pdh.getId()else", String.valueOf(product_data_holder.getId()));
        Log.e("id",addToCartReq.getProductId());
        Log.e("quantity",addToCartReq.getQuantity());
        Log.e("object",addToCartReq.toString());
        call = apiService.addToCart("Bearer " + sessionManager.getKeySession(),addToCartReq);*/

        if (product_data_holder.getType().equals("variable")) {
            Log.e("pdh.getId()variabve", String.valueOf(product_data_holder.getId()));
            Log.e("token++",sessionManager.getKeySession().trim());
            AddToCartWithVariationReq addToCartWithReq = new AddToCartWithVariationReq(String.valueOf(product_data_holder.getId()),selected_variation,selected_quantity);
            Log.e("id variation",addToCartWithReq.getProduct_id());
            Log.e("quantity variation",addToCartWithReq.getQuantity());
            Log.e("object variation",addToCartWithReq.toString());
            call = apiService.addToCart("Bearer " + sessionManager.getKeySession(),addToCartWithReq);
        }
        else{
             addToCartReq = new AddToCartReq(String.valueOf(product_data_holder.getId()),selected_quantity);
            Log.e("pdh.getId()else", String.valueOf(product_data_holder.getId()));
            Log.e("id",addToCartReq.getProduct_id());
            Log.e("quantity",addToCartReq.getQuantity());
            Log.e("object",addToCartReq.toString());
            call = apiService.addToCart("Bearer " + sessionManager.getKeySession(),addToCartReq);
        }

        call.enqueue(new Callback<newAddToCartResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<newAddToCartResponse> call, Response<newAddToCartResponse> response) {
                if(response.isSuccessful()){
                    Log.e("response on add", String.valueOf(response.body()));
                    Toast.makeText(getApplicationContext(), "Added to cart success", Toast.LENGTH_SHORT).show();
                    Log.e("response boody cp 2222222222", String.valueOf(response.body().getKey())+"data want to print "+response.raw().request().headers().get("Cookie"));

                    Log.e("==Cookie==1===", "==="+response.raw().headers().get("Set-Cookie") );
                    Log.e("==Cookie==2==", "==="+response.headers().get("Cookie"));
                    Log.e("==Content-Type====", "==="+response.headers().get("Content-Type"));
                    addToCartResponse = new newAddToCartResponse();
                    product_data_holder.setmRefrenceKey(response.body().getKey());
                }else{
                    Log.e("Error","");
                }
            }

            @Override
            public void onFailure(Call<newAddToCartResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });

    }


    /*this bunch of code is the method to test product is having varition or not*/
    public ProductVariationContainer is_having_variations(ProductListingsModeResponse data_object){
        ProductVariationContainer productVariationContainer = new ProductVariationContainer();
        if (!data_object.getVariations().isEmpty()) {
            HashMap<String, CustomVariations> spinnerMap = new HashMap<>();
            for (int k = 0; k <= data_object.getVariations().size(); k++) {
                CustomVariations variations = new CustomVariations();
                try {
                    variations.setId(data_object.getVariations().get(k).getId());
                    variations.setIn_stock(data_object.getVariations().get(k).getInStock());
                    variations.setWeight(data_object.getVariations().get(k).getWeight());
                    variations.setPrice(data_object.getVariations().get(k).getPrice());
                    for (int l = 0; l <= data_object.getAttributes().size(); l++) {
                        variations.setName(data_object.getVariations().get(k).getAttributes().get(l).getName());
                        variations.setOption(data_object.getVariations().get(k).getAttributes().get(l).getOption()+"  "+getResources().getString(R.string.rs)+data_object.getVariations().get(k).getPrice());
                        spinnerMap.put(data_object.getVariations().get(k).getPrice(), variations);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                productVariationContainer.setVariations(variations);
                productVariationContainer.setQuantity_attributes(spinnerMap);

            }
            return productVariationContainer;
        }
        else{
            Log.e("TAG","no variation is provided");
            return null;
        }
    }

    /*show success dialog for add to cart succesfully*/
    private void showCustomDialog() {
        //before inflating the custom alert dialog highlight_remove, we will get the current activity viewgroup
        ViewGroup viewGroup =findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(ProductListings.this).inflate(R.layout.my_dialog, viewGroup, false);
        Button btn = (Button) dialogView.findViewById(R.id.buttonOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getApplicationContext(),NewCategoryActivity.class);
                startActivity(intent);*/

                finish();
            }
        });

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductListings.this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
