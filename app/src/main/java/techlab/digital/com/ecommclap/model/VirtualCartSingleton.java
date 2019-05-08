package techlab.digital.com.ecommclap.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponsetTwo;

public class VirtualCartSingleton extends Application {
    List<ProductListingsModeResponsetTwo> mFinalList_for_cart = new ArrayList<>();

    public static final String TAG = VirtualCartSingleton.class.getSimpleName();
    private VirtualCartSingleton(){

    }

    private static VirtualCartSingleton mInstance;
    public static VirtualCartSingleton getInstance() {
        if(mInstance == null) {
            mInstance = new VirtualCartSingleton();
        }
        return mInstance;
    }

    public List<ProductListingsModeResponsetTwo> getmFinalList_for_cart() {
        return mFinalList_for_cart;
    }

    public void setmFinalList_for_cart(List<ProductListingsModeResponsetTwo> mFinalList_for_cart) {
        this.mFinalList_for_cart = mFinalList_for_cart;
    }
}
