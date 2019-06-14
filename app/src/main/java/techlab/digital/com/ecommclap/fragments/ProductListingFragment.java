package techlab.digital.com.ecommclap.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.AllServiceActivity;
import techlab.digital.com.ecommclap.activity.CheckOutServiceActivity;
import techlab.digital.com.ecommclap.activity.ProductListings;
import techlab.digital.com.ecommclap.adapter.ProductListingsAdapter;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartReq;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartResponse;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartWithVariationReq;
import techlab.digital.com.ecommclap.model.fetchSubProducts.CustomVariations;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductVariationContainer;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ProductListingFragment extends Fragment{






    int product_id;
    String category_name;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.noResults)
    TextView noResults;
    String p_variation,p_quantity;

    SessionManager sessionManager;
    // variable to track event time`
    private long mLastClickTime = 0;

    AddToCartResponse addToCartResponse;

    List<ProductListingsModeResponse> product_data_List;


    ProductListingsModeResponse productListingsModeResponse2 ;


















    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_listing_fragment, container, false);
        ButterKnife.bind(this,view);
        ButterKnife.bind(this,view);
        sessionManager = new SessionManager(getContext());

        fetchproducts("testing");

        return view;

    }



    ProgressDialog progressDialog;
    private void fetchproducts(String str){


        progressDialog = new ProgressDialog(getActivity());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);

        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductListingsModeResponse>> call = apiService.getProducts(sessionManager.getUserCity(),str);

        call.enqueue(new Callback<List<ProductListingsModeResponse>>() {
            @Override
            public void onResponse(Call<List<ProductListingsModeResponse>> call, Response<List<ProductListingsModeResponse>> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){

                   //setAdapterViews(response.body());
                }else{
                    Log.e("Error","");

                }
            }

            @Override
            public void onFailure(Call<List<ProductListingsModeResponse>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });

    }
    ProductListingsAdapter mAdapter;

  /*  private void setAdapterViews(List<ProductListingsModeResponse> datumList){
        product_data_List = datumList;
        mAdapter = new ProductListingsAdapter(getContext(), datumList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
       *//* mAdapter.mCallback = (ProductListingsAdapter.OnAddProductButtonListener)this;
        mAdapter.mCallback2 = (ProductListingsAdapter.OnInterfaceListener2)this;
        mAdapter.mCallback3 = (ProductListingsAdapter.OnServiceBooked)this;*//*
    }
*/



/*


interface call back to add to cart the product

    @Override
    public void OnAddProduct(View view, int position,ProductListingsModeResponse object) {
        ProductListingsModeResponse  product_object_data = object;
        if (product_object_data.getType().equals("phive_booking")){
            //intent = new Intent(context, ServiceDescriptionActivity.class);
        }else {
            ProductVariationsSheet frgaments = new ProductVariationsSheet(getActivity(),product_object_data,position);
            frgaments.show(getFragmentManager(), "ahahahaha");
            frgaments.setCancelable(false);
        }
    }

interface call back chose add new product or update last one

    @Override
    public void OnAddOrRepeat(View view, int position,ProductListingsModeResponse m_data) {
productListingsModeResponse.set

        AddNewOrRepeatBottomSheet fragmentEliminaPost = new AddNewOrRepeatBottomSheet(getContext(),m_data,position);
        fragmentEliminaPost.show(getFragmentManager(), "ahahahaha");
        fragmentEliminaPost.setCancelable(false);
    }

Interface caLLBack to get User Choice wheather he wants to add new product or repeat last one

    @Override
    public void selected_option_is(String option,ProductListingsModeResponse m_data_object,int data_position) {

        if(option.equals("add"))
        {
            ProductVariationContainer m_variation_container = is_having_variations(m_data_object);
            if(!m_variation_container.equals(null))
            {
                ProductVariationsSheet fragmentEliminaPost = new ProductVariationsSheet(getContext(),m_data_object,data_position);
                fragmentEliminaPost.show(getFragmentManager(), "ahahahaha");
                fragmentEliminaPost.setCancelable(false);
            }
            else
                Log.e("TAG","no variation is provided");
        }
        else if(option.equals("repeat")){

            UpdateQuantityOnlySheet fragmentEliminaPost = new UpdateQuantityOnlySheet(getContext(),m_data_object);
            fragmentEliminaPost.show(getFragmentManager(), "ahahahaha");
            fragmentEliminaPost.setCancelable(false);
        }


    }

this method is used to update the quantity and variations of the product

    @Override
    public void change_variation_is(String variation,String quantity,ProductListingsModeResponse add_cart_product_response,int product_position) {

        int updateIndex = product_position;
        mAdapter.update_elegent(updateIndex,quantity);
        //mAdapter.notifyItemChanged(updateIndex);
        p_variation = variation;
        p_quantity = quantity;

        addtoCartProduct(add_cart_product_response);

    }

interface call back to update the quantity only

    @Override
    public void update_quantity_only(String quantity,ProductListingsModeResponse object_data) {

        //productListingsModeResponse = new ProductListingsModeResponse();
        // productListingsModeResponse.setStockQuantity(quantity);
        updateQuantity(object_data,quantity);

        Toast.makeText(getContext(),quantity,Toast.LENGTH_SHORT).show();
    }

interface call back to get service date from bottom sheet

    @Override
    public void onbookService(ProductListingsModeResponse data) {
        productListingsModeResponse2 = new ProductListingsModeResponse();
        productListingsModeResponse2 = data;
        BookServiceBottomSheet fragmentEliminaPost = new BookServiceBottomSheet(getContext());
        fragmentEliminaPost.show(getFragmentManager(), "ahahahaha");



    }

    @Override
    public void selected_date_is(String date) {
        Toast.makeText(getContext(),"date is"+date,Toast.LENGTH_SHORT).show();

        if (sessionManager.isLoggedIn()){

            Intent intent = new Intent(getContext(), CheckOutServiceActivity.class);
            intent.putExtra("service_date",date);
            intent.putExtra("object", productListingsModeResponse2);
            startActivity(intent);
        }
        else {
            sessionManager.checkLogin();
        }


    }

network calling to update the quantiity

    private void updateQuantity(ProductListingsModeResponse response,String quantity){

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.updateQuantity("Bearer " + sessionManager.getKeySession(),response.getmRefrenceKey(),quantity);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"Item Updated Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("Error","");
                    if (response.code() == 403){


                    }
                  // finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

            }
        });


    }

network calling to add to cart to server and app ass well

    private void addtoCartProduct(final ProductListingsModeResponse product_data_holder){
        Call<AddToCartResponse> call = null;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        if (product_data_holder.getType().equals("variable")) {
            AddToCartWithVariationReq addToCartWithReq = new AddToCartWithVariationReq(String.valueOf(product_data_holder.getId()),p_variation,String.valueOf(p_quantity));
            call = apiService.addToCart("Bearer " + sessionManager.getKeySession(),addToCartWithReq);
        }
        else{

            AddToCartReq addToCartReq = new AddToCartReq(String.valueOf(product_data_holder.getId()),String.valueOf(p_quantity));
            call = apiService.addToCart("Bearer " + sessionManager.getKeySession(),addToCartReq);
        }


        call.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Added to cart success", Toast.LENGTH_SHORT).show();
                    Log.e("response boody cp ", String.valueOf(response.body().getKey()));
                    addToCartResponse = new AddToCartResponse();
                    //  addToCartResponse.setReference_key(response.body().getKey());

                    product_data_holder.setmRefrenceKey(response.body().getKey());

                }else{
                    Log.e("Error","");
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });

    }


this is the method to test product is having varition or not

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




*/





}
