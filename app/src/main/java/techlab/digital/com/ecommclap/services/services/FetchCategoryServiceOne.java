package techlab.digital.com.ecommclap.services.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.activity.SelectCityActivity;
import techlab.digital.com.ecommclap.app.Prefs;
import techlab.digital.com.ecommclap.model.CityLocationModel.CityLocationResponse;
import techlab.digital.com.ecommclap.model.fetch_category.Category;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

@SuppressLint("Registered")
public class FetchCategoryServiceOne extends Service {
    Realm mRealm;
SessionManager sessionManager;
    @Override
    public void onCreate() {
        mRealm = Realm.getDefaultInstance();
        //*********fetching category from server and updating to realm database******************//
        sessionManager = new SessionManager(getApplicationContext());
        fetch_user_location();
        fetchCategoriesItems_and_Update();


    }
    private void fetch_user_location() {



            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CityLocationResponse>> call = apiService.fetchCityLocation();
            call.enqueue(new Callback<List<CityLocationResponse>>() {
                @Override
                public void onResponse(Call<List<CityLocationResponse>> call, Response<List<CityLocationResponse>> response) {
                    if(response.isSuccessful()){
                        // setAdapterViews(response.body());
                        if (!response.body().isEmpty()) {
                            Log.e("Calling new service", String.valueOf(response.body()));
                            update_location(response.body());
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Not Avai    lable",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.e("Error","");
                    }
                }
                @Override
                public void onFailure(Call<List<CityLocationResponse>> call, Throwable t) {
                    Log.e("onFailure",t.getMessage());

                }
            });

    }

    private void update_location(List<CityLocationResponse> body) {
        for(int i=0;i<body.size();i++)
        {
            if(body.get(i).getName().equals(sessionManager.getUserCity()))
            {
                sessionManager.setUserCity(body.get(i).getName());
                sessionManager.setKeySelectCityDescrption(body.get(i).getDescription());

            }
        }

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void fetchCategoriesItems_and_Update() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Category>> call = apiService.getCategories(0, 50);

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Sorry Categories not found", Toast.LENGTH_SHORT).show();
                    } else {
                        updateRealmDatabase(response.body());
                        //update(response.body());
                    }

                } else {
                    Log.e("Error", "");

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());

            }
        });
    }

    @SuppressLint("LongLogTag")
    private void updateRealmDatabase(List<Category> body) {
        try {
            if (body.get(0).getSlug().equals("uncategorised")) {
                body.remove(0);
            }
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        List<CategoryRealmDb> category_list = new ArrayList<>();


        try {
            for (Category country : body) {
                CategoryRealmDb category = new CategoryRealmDb();
                category.setDescription(country.getDescription());
                category.setSlug(country.getSlug());
                category.setName(country.getName());
                category.setId(country.getId());
                category.setImage(country.getImage().getSrc());
                category_list.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        RealmResults<CategoryRealmDb> results = mRealm.where(CategoryRealmDb.class).findAll();
        mRealm.beginTransaction();

        for (int i = 0; i < category_list.size(); i++) {

            try {
                results.get(i).setId(category_list.get(i).getId());
                results.get(i).setName(category_list.get(i).getImage());
                results.get(i).setSlug(category_list.get(i).getSlug());
                results.get(i).setImage(category_list.get(i).getImage());
                results.get(i).setDescription(category_list.get(i).getDescription());

            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }
        mRealm.copyToRealm(results);
        mRealm.commitTransaction();


        RealmResults<CategoryRealmDb> results2 = mRealm.where(CategoryRealmDb.class).findAll();

        //setting broadcast reciever
        Intent local = new Intent();
        local.setAction("com.hello.action");
        this.sendBroadcast(local);
        Prefs.with(this).setPreLoad(true);

    }


    //new update function to update realm databse
    @SuppressLint("LongLogTag")
    public void update(List<Category> body) {
        if (body.get(0).getSlug().equals("uncategorised")) {
            body.remove(0);
        }
        String id = String.valueOf(body.get(1).getId());

        List<CategoryRealmDb> mcategory_list = new ArrayList<>();

        try {
            for (Category mcountry : body) {

                CategoryRealmDb category = new CategoryRealmDb();
                category.setDescription(mcountry.getDescription());
                category.setSlug(mcountry.getSlug());
                category.setName(mcountry.getName());
                category.setId(mcountry.getId());
                category.setImage(mcountry.getImage().getSrc());
                Log.e("list data from server side : : ",mcountry.getName()+mcountry.getDescription()+mcountry.getId()+mcountry.getSlug());
                mcategory_list.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RealmResults<CategoryRealmDb> university = mRealm.where(CategoryRealmDb.class).equalTo("id", id).findAll();


    }
}
