package techlab.digital.com.ecommclap.utility;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import techlab.digital.com.ecommclap.model.cartModel.FetechCart.FetchCartResponse;

public class JsonUtils {

    @Nullable
    public static FetchCartResponse getModelFromJson(String json){
        try{
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, FetchCartResponse.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
