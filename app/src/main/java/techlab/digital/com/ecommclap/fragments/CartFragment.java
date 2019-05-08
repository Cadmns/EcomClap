package techlab.digital.com.ecommclap.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.CheckoutProductActivity;
import techlab.digital.com.ecommclap.activity.UserConfirmation;
import techlab.digital.com.ecommclap.adapter.CartAdapter;
import techlab.digital.com.ecommclap.model.cartModel.FetechCart.FetchCartResponse;
import techlab.digital.com.ecommclap.model.cartTotal.CartTotalResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.JsonUtils;
import techlab.digital.com.ecommclap.utility.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements CartAdapter.OnInterfaceListener {
    View view;
    @BindView(R.id.container)
    CoordinatorLayout mCoordinatorLayout;
    SessionManager session;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.text_action_bottom1)
    TextView mToalPayment;
    @BindView(R.id.whole_payment_summary)
    TextView mToalViewPayment;
    @BindView(R.id.text_action_bottom2)
    TextView mProceed;
    LinearLayout layout_view_details,layoutCartItems,layoutCartPayments,layoutCartNoItems,layoutCartLoginNeeded;
    // variable to track event time
    private long mLastClickTime = 0;
    // JsonObject
    CartAdapter mAdapter;
    List<FetchCartResponse> mFinalTotal = new ArrayList<>();
    List<FetchCartResponse> mCloneProducts = new ArrayList<>();
    String shipping_charge,product_charge;
    String total_cart_amount;
    String tag;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        layout_view_details = view.findViewById(R.id.view_payment_total);
        ButterKnife.bind(this,view);
        session = new SessionManager(getContext());
        readBundle(getArguments());

        mProceed.setVisibility(View.GONE);
        setCartLayout();
        layout_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewTotalPaymentDetailsSheet fragment = new ViewTotalPaymentDetailsSheet(product_charge,shipping_charge);
                fragment.show(getFragmentManager(), "TAG");

            }
        });

        return view;
    }

    public static CartFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);

        CartFragment fragment = new CartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            tag = bundle.getString("tag");

        }
    }

    private Boolean mCheckInternetWithMultipleClicks(){

        if (CheckInternet.isNetwork(getContext())) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;
            //  }
        }else {
            //do something, net is not connected

            Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

        }

        return false;

    }

    private void setCartLayout(){
        layoutCartItems = (LinearLayout) view.findViewById(R.id.layout_items);
        layoutCartPayments = (LinearLayout)  view.findViewById(R.id.layout_payment);
        layoutCartNoItems = (LinearLayout)  view.findViewById(R.id.layout_cart_empty);
        layoutCartLoginNeeded = (LinearLayout)  view.findViewById(R.id.layout_login_empty);

        if (session.isLoggedIn()) {
            if (mCheckInternetWithMultipleClicks()) {
                fetchCartItems();

            }
        }
        else {

            layoutCartLoginNeeded.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);
            layoutCartNoItems.setVisibility(View.GONE);

            Button bStartShopping = (Button)view.findViewById(R.id.btn_login);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (session.isNumberVerified()){
                        //session.checkLogin();
                        session.checkLogin();
                    }
                    else{
                        Intent intent = new Intent(getContext(),UserConfirmation.class);
                        startActivity(intent);
                    }
                }
            });

        }



    }


    @OnClick(R.id.text_action_bottom2)
    public void proceddToCheckOut(View view){
        if (mCheckInternetWithMultipleClicks()){
            Intent intent = new Intent(getContext(), CheckoutProductActivity.class);

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("cart_items", (ArrayList<? extends Parcelable>) mFinalTotal);
            bundle.putString("cart_total",total_cart_amount);
            bundle.putString("shipping_total",shipping_charge);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    ProgressDialog progressDialog;
    private void fetchCartItems(){
        progressDialog = new ProgressDialog(getContext());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call call = apiService.getCartItems("Bearer " + session.getKeySession(),true);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if(response.isSuccessful()) {
                    if (response.body().equals("Cart is empty!")) {

                        layoutCartNoItems.setVisibility(View.VISIBLE);
                        layoutCartPayments.setVisibility(View.GONE);
                    }

                    if (new Gson().toJson(response.body()).contains("key")) {



                        layoutCartLoginNeeded.setVisibility(View.GONE);
                        layoutCartItems.setVisibility(View.VISIBLE);
                        layoutCartPayments.setVisibility(View.VISIBLE);
                        layoutCartNoItems.setVisibility(View.GONE);

                        if (tag.equals("product"))
                            layoutCartPayments.setVisibility(View.GONE);
                        else
                            layoutCartPayments.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            Iterator<?> keys = jsonObject.keys();
                            List<FetchCartResponse> mFinalCartList = new ArrayList<>();
                            while (keys.hasNext()) {
                                String key = (String) keys.next();
                                if (jsonObject.get(key) instanceof JSONObject) {

                                    JSONObject value = jsonObject.getJSONObject(key);
                                    FetchCartResponse projectsList = JsonUtils.getModelFromJson(value.toString());
                                    mFinalCartList.add(projectsList);

                                }
                            }
                            setAdapter(mFinalCartList);

                            getCartTotal();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else{

                        layoutCartLoginNeeded.setVisibility(View.GONE);
                        layoutCartItems.setVisibility(View.GONE);
                        layoutCartPayments.setVisibility(View.GONE);
                        layoutCartNoItems.setVisibility(View.VISIBLE);
                        Log.e("Error","");
                    }
                }
            }

            @Override
            public void onFailure(Call  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }


    private void setAdapter(List<FetchCartResponse> mItemsList){
        mFinalTotal = new ArrayList<>();
        mFinalTotal.addAll(mItemsList);

        mCloneProducts.addAll(mItemsList);
        if (tag.equals("product")) {
            ((CheckoutProductActivity) getActivity()).fetchCartProductsId(mFinalTotal);
        }
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(recylerViewLayoutManager);
        mAdapter = new CartAdapter(getContext(),mItemsList);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.mCallback = (CartAdapter.OnInterfaceListener) this;

    }

    private void removeItemsCart(String key, final int position){

        progressDialog = new ProgressDialog(getContext());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.deleteItems("Bearer " + session.getKeySession(),key);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){

                    Toast.makeText(getContext(),"Remove successfull",Toast.LENGTH_SHORT).show();
                 //removeAt();
                    getCartTotal();


                }else{
                    Log.e("Error","");
                    if (response.code() == 403){



                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });


    }

    @Override
    public void onRemoveItemsClick(View view, int position) {

        try {
            FetchCartResponse fetchCartResponse = mFinalTotal.get(position);
            removeItemsCart(fetchCartResponse.getKey(),position);
        }catch (Exception exception){
            exception.printStackTrace();
            Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRemoveItemsClick2(String key) {
        try {
            removeItemsCart2(key);
        }catch (Exception exception){
            exception.printStackTrace();
            Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void removeAt() {
        mAdapter.removeItem(layoutCartNoItems,layoutCartPayments);

    }

    @Override
    public void onUpdateItemsClick(View view, int position, int quantity) {
        FetchCartResponse fetchCartResponse = mFinalTotal.get(position);
        try {
            updateQuantity(fetchCartResponse.getKey(), String.valueOf(quantity));
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
        }

    }


    private void getCartTotal(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CartTotalResponse> call = apiService.getCartTotal("Bearer " + session.getKeySession(),session.getUserId());

        call.enqueue(new Callback<CartTotalResponse>() {
            @Override
            public void onResponse(Call<CartTotalResponse> call, Response<CartTotalResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getTotal().equals(0)){
                        layoutCartNoItems.setVisibility(View.VISIBLE);
                        layoutCartPayments.setVisibility(View.GONE);
                    }
                    else{
                        mProceed.setVisibility(View.VISIBLE);
                        mToalPayment.setText("Rs : " + response.body().getTotal());
                        total_cart_amount = response.body().getTotal();
                        product_charge = response.body().getSubtotal();
                        shipping_charge = response.body().getShippingTotal();
                    }



                }else{
                    if (response.code() == 403){

                    }

                }
            }

            @Override
            public void onFailure(Call<CartTotalResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

            }
        });

    }

    private void updateQuantity(String key,String quantity){
        progressDialog = new ProgressDialog(getContext());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.updateQuantity("Bearer " + session.getKeySession(),key,quantity);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    setCartLayout();
                    Toast.makeText(getContext(),"Item Updated Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("Error","");
                    if (response.code() == 403){


                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });


    }



    private void removeItemsCart2(String key){
        progressDialog = new ProgressDialog(getContext());
        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.deleteItems("Bearer " + session.getKeySession(),key);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){

                    Toast.makeText(getContext(),"Remove from server successfull",Toast.LENGTH_SHORT).show();
                    removeAt();
                    getCartTotal();


                }else{
                    Log.e("Error","");
                    if (response.code() == 403){



                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });


    }



}
