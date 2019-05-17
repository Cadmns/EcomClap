package techlab.digital.com.ecommclap.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.NewCategoryActivity;
import techlab.digital.com.ecommclap.activity.ProductDescriptionActivity;
import techlab.digital.com.ecommclap.adapter.ProductListingsAdapter;
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

@SuppressLint("ValidFragment")
public class ProductVariationsSheet extends BottomSheetDialogFragment{
    Spinner mVariations;
    Dialog mdialog;
    ArrayList<CustomVariations> mVariationsIdList = new ArrayList<>();
    SessionManager sessionManager;
    ArrayList<String> arrayList;
    ProductListingsModeResponse response_data ;
    ProductVariationContainer productVariationContainer;
    Context context;
    String temp_price,temp_Quantity_Size,temp_variations;
    Button done_btn;
    ElegantNumberButton  mQuantity;
    // variable to track event time`
    private long mLastClickTime = 0;
    int product_position;

    onVariationChanged onVariationChanged;
    String number_quantity;
    ProductListingsAdapter.ViewHolder viewHolder;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onVariationChanged = (onVariationChanged) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }



    @SuppressLint("ValidFragment")
    public ProductVariationsSheet(Context mtx, ProductListingsModeResponse datum, int postion, String quantity, ProductListingsAdapter.ViewHolder mholder){
        this.response_data = datum;
        this.productVariationContainer = productVariationContainer;
        context = mtx;
        product_position = postion;
        number_quantity = quantity;
        viewHolder = mholder;



    }


    @SuppressLint("ValidFragment")
    public ProductVariationsSheet(Context mtx){
        context = mtx;
    }




    @SuppressLint("RestrictedApi")
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        mdialog = dialog;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.product_variations_sheet, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        done_btn = view.findViewById(R.id.done_btn_variation);
        mQuantity = view.findViewById(R.id.elegenty_quantity_btn);
        mQuantity.setNumber(number_quantity);
        sessionManager = new SessionManager(getContext());
        mVariations = view.findViewById(R.id.product_variations);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (sessionManager.isLoggedIn()){
                    if (mCheckInternetWithMultipleClicks()) {
                        onVariationChanged.change_variation_is(temp_variations,mQuantity.getNumber(),response_data,product_position,viewHolder);
                    }
                }else {
                    sessionManager.checkLogin();
                }

            }
        });

        /*here is the peace of code to know is product having variations or not*/
        ProductVariationContainer productVariationContainer = new ProductVariationContainer();

        if (!response_data.getVariations().isEmpty()) {
            HashMap<String, CustomVariations> spinnerMap = new HashMap<>();

            for (int k = 0; k <= response_data.getVariations().size(); k++) {
                CustomVariations variations = new CustomVariations();

                try {

                    variations.setId(response_data.getVariations().get(k).getId());
                    variations.setIn_stock(response_data.getVariations().get(k).getInStock());
                    variations.setWeight(response_data.getVariations().get(k).getWeight());
                    variations.setPrice(response_data.getVariations().get(k).getPrice());

                    for (int l = 0; l <= response_data.getAttributes().size(); l++) {

                        variations.setName(response_data.getVariations().get(k).getAttributes().get(l).getName());
                        variations.setOption(response_data.getVariations().get(k).getAttributes().get(l).getOption()+"  "+getResources().getString(R.string.rs)+response_data.getVariations().get(k).getPrice());
                        spinnerMap.put(response_data.getVariations().get(k).getPrice(), variations);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                productVariationContainer.setVariations(variations);
                productVariationContainer.setQuantity_attributes(spinnerMap);

            }

            paths= productVariationContainer.getQuantity_attributes();
            checkvariations();
        }
        else{
            mVariations.setVisibility(View.GONE);
            Log.e("TAG","______cpno variation is provided");
        }


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }


    }



    //*Checking variations for product*/

    private void checkvariations(){
        if (!response_data.getAttributes().isEmpty())

            initializeUIForVariations();
        else
            mVariations.setVisibility(View.GONE);

    }


    private static HashMap<String,CustomVariations> paths;
    private void initializeUIForVariations() {
        arrayList = new ArrayList<>();
        for (Map.Entry<String, CustomVariations> entry : paths.entrySet()) {
            System.out.println("cp.. "+entry.getKey() + " = " + entry.getValue());
            //   arrayList = new ArrayList<>();
            arrayList.add(entry.getValue().getOption());
        }
        temp_price = response_data.getPrice();
        temp_Quantity_Size = arrayList.get(0).toString();
        temp_variations = String.valueOf(paths.entrySet().iterator().next().getValue().getId());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mVariations.setAdapter(adapter);

        //mVariations.setOnItemSelectedListener(context);
        int selectedVariationId=0;
        mVariations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint({"LogNotTimber", "LongLogTag"})
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.e("Slected Variationd--- ", String.valueOf(mVariations.getSelectedItem()));

                String selectedItemTitle = mVariations.getSelectedItem().toString();
                for (Map.Entry<String, CustomVariations> entry : paths.entrySet()) {
                    System.out.println(entry.getKey() + " = " + entry.getValue());

                    if (selectedItemTitle.equals(entry.getValue().getOption())){
                        //  itemPrice.setText("Rs."+entry.getKey());
                        //mBookingPrice.setText("Price : "+entry.getKey() + " Rs ");

                        temp_price = entry.getKey();

                        temp_variations = String.valueOf(entry.getValue().getId());
                        temp_Quantity_Size = entry.getValue().getOption();
                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private Boolean mCheckInternetWithMultipleClicks(){

        if (CheckInternet.isNetwork(getContext())) {
            /* if (sessionManager.isLoggedIn()) {*/
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


    /*add to cart coding start from here*/





    public interface onVariationChanged {
        public void change_variation_is(String variation, String Quantity, ProductListingsModeResponse data_object, int product_position, ProductListingsAdapter.ViewHolder holder);
    }


}
