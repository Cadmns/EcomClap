package techlab.digital.com.ecommclap.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.UserConfirmation;
import techlab.digital.com.ecommclap.model.phoneNumberConfirmation.NumberConfrimResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;

public class Utils {

    static SessionManager sessionManager;
    static ProgressDialog progressDialog;
    public static void checkUserCredentials(final String phone_number, Context context, RevealCourtPlaceCallbacks revealCourtPlaceCallbacks){

        progressDialog = new ProgressDialog(context);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);

        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonArray> call = apiService.userNumberConfirmation(phone_number);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()){
                    try {
                        JSONArray jsonArray = new JSONArray(String.valueOf(response.body()));


                        if (jsonArray.isNull(0)){
                            /*New phone number so redirect to the main page*/

                            sessionManager.setNumberVerifiedSatusFlag(true);

                        }
                        else {
                            //genratingList(jsonArray);



                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e("Error","");

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }
    private void genratingList(JSONArray jsonArray){
        ArrayList<NumberConfrimResponse> mFinalList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<NumberConfrimResponse>>(){}.getType());



    }


    public interface RevealCourtPlaceCallbacks {
        void onSuccess(@NonNull String value);

        void onError(@NonNull Throwable throwable);
    }




}
