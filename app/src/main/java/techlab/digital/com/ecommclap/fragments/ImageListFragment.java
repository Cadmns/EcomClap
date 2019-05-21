/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package techlab.digital.com.ecommclap.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.AllServiceActivity;
import techlab.digital.com.ecommclap.activity.ProductListings;
import techlab.digital.com.ecommclap.adapter.ProductListingsAdapter;
import techlab.digital.com.ecommclap.adapter.SubCategoriesAdapter;
import techlab.digital.com.ecommclap.model.categories.subCategories.FetchSubCategory;
import techlab.digital.com.ecommclap.model.categories.subCategories.Image;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;


public class ImageListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener ,ProductListingsAdapter.OnInterfaceListener2,ProductListingsAdapter.OnAddProductButtonListener,ProductVariationsSheet.onVariationChanged {
  //  ArrayList<ProductVariationContainer> productItemArrayList;
    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    private static AllServiceActivity mActivity;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    @BindView(R.id.eta_container)
    LinearLayout mEtaContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    View view;
    @BindView(R.id.eta_arrival)
    TextView mCategoryEta;
    @BindView(R.id.noResults)
    TextView mNoResults;
    String cat_slug;
    onSubCatNotFoundCallBack catNotFoundCallBack;




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            catNotFoundCallBack = (onSubCatNotFoundCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AllServiceActivity) getActivity();
    }

    // variable to track event time
    private long mLastClickTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_sub_categories, container, false);
        ButterKnife.bind(this,view);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupRecyclerView();
        return view;
    }



    private void setupRecyclerView() {
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mCheckInternetWithMultipleClicks()) {
                                            fetchCategoriesItems(ImageListFragment.this.getArguments().getInt("type"));
                                        }
                                    }
                                }
        );


    }

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter mAdapter;

    private Boolean mCheckInternetWithMultipleClicks(){
        if (getContext()!= null) {
            if (CheckInternet.isNetwork(getContext())) {
                /* if (sessionManager.isLoggedIn()) {*/
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return false;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                return true;
                //}
            } else {
                //do something, net is not connected

                Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

            }
        }
        return false;

    }
    private void fetchCategoriesItems(final int str){
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<FetchSubCategory>> call = apiService.getSubcategories(str);
        call.enqueue(new Callback<List<FetchSubCategory>>() {
            @Override
            public void onResponse(Call<List<FetchSubCategory>> call, Response<List<FetchSubCategory>> response) {
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful()){
                    if (response.body().isEmpty()){
                       // mNoResults.setVisibility(View.VISIBLE);
                        fetchproducts(ImageListFragment.this.getArguments().getString("cat_slug"));
              }
                    else
                    createAdapter(response.body());
                }else{
                    Log.e("Error","");
                        mNoResults.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<FetchSubCategory>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

            }
        });

    }




    private void createAdapter(List<FetchSubCategory> body){
        recyclerView= view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        // recyclerView.setHasFixedSize(true);
        mAdapter = new SubCategoriesAdapter(this.getActivity(), body);

        recyclerView.setAdapter(mAdapter);
        mEtaContainer.setVisibility(View.VISIBLE);
        mCategoryEta.setText(ImageListFragment.this.getArguments().getString("eta"));

    }
    @Override
    public void onRefresh() {

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if (mCheckInternetWithMultipleClicks()) {
            fetchCategoriesItems(ImageListFragment.this.getArguments().getInt("type"));
        }
            }

                                 }
        );
    }

    @Override
    public void change_variation_is(String variation, String Quantity, ProductListingsModeResponse data_object, int product_position, ProductListingsAdapter.ViewHolder holder) {



    }




    public interface onSubCatNotFoundCallBack {
        public void on_product_found(ProductListingsAdapter adapter);
    }





    @Override
    public void OnAddProduct(View view, int position, ProductListingsModeResponse data, String quantity, ProductListingsAdapter.ViewHolder mholder) {
        ProductListingsModeResponse  product_object_data = data;
        ProductVariationsSheet frgaments = new ProductVariationsSheet(getContext(),product_object_data,position,quantity,mholder);
        frgaments.show(getFragmentManager(), "ahahahaha");
        frgaments.setCancelable(false);
    }

    /*interface call back chose add new product or update last one*/
    @Override
    public void OnAddOrRepeat(View view, int position, ProductListingsModeResponse m_data, String quantity, ProductListingsAdapter.ViewHolder holder) {
        AddNewOrRepeatBottomSheet fragmentEliminaPost = new AddNewOrRepeatBottomSheet(getContext(),m_data,position,quantity,holder);
        fragmentEliminaPost.show(getFragmentManager(), "ahahahaha");
        fragmentEliminaPost.setCancelable(false);
    }

























    ProgressDialog progressDialog;
    // Setting up Progress dialog for loading the content.
    private void fetchproducts(String str){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductListingsModeResponse>> call = apiService.getProducts(str);
        call.enqueue(new Callback<List<ProductListingsModeResponse>>() {
            @Override
            public void onResponse(Call<List<ProductListingsModeResponse>> call, Response<List<ProductListingsModeResponse>> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    setAdapterViews(response.body());
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

    ProductListingsAdapter mAdapter2;
    private void setAdapterViews(List<ProductListingsModeResponse> datumList){
        recyclerView2= view.findViewById(R.id.recyclerview2);
        recyclerView2.setVisibility(View.VISIBLE);
        mAdapter2 = new ProductListingsAdapter(getContext(), datumList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(mAdapter2);
        catNotFoundCallBack.on_product_found(mAdapter2);
        mAdapter2.mCallback = (ProductListingsAdapter.OnAddProductButtonListener)this;
        mAdapter2.mCallback2 = (ProductListingsAdapter.OnInterfaceListener2)this;
       /* mAdapter.mCallback = (ProductListingsAdapter.OnAddProductButtonListener)this;
        mAdapter.mCallback2 = (ProductListingsAdapter.OnInterfaceListener2)this;
        mAdapter.mCallback3 = (ProductListingsAdapter.OnServiceBooked)this;*/
    }

















}
