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

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import techlab.digital.com.ecommclap.adapter.SubCategoriesAdapter;
import techlab.digital.com.ecommclap.model.categories.subCategories.FetchSubCategory;
import techlab.digital.com.ecommclap.model.categories.subCategories.Image;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;


public class ImageListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
  //  ArrayList<ProductVariationContainer> productItemArrayList;
    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    private static AllServiceActivity mActivity;
    RecyclerView recyclerView;
    @BindView(R.id.eta_container)
    RelativeLayout mEtaContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    View view;
    @BindView(R.id.eta_arrival)
    TextView mCategoryEta;

    @BindView(R.id.noResults)
    TextView mNoResults;
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
                        mNoResults.setVisibility(View.VISIBLE);
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
}
