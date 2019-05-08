package techlab.digital.com.ecommclap.model.fetch_category;/*
package techlab.digital.com.ecommclap.model.fetch_category;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.CategoryAdapter;
import techlab.digital.com.ecommclap.adapter.RealmCategoryAdapter;
import techlab.digital.com.ecommclap.app.Prefs;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.realm.RealmController;

public class MMainActivity extends AppCompatActivity {


    private CategoryAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmain);

        recycler = (RecyclerView) findViewById(R.id.lrecycler);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();
        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecycler();
        fetchCategoriesItems();

       */
/* if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }
        else {
            setRealmData();
        }*//*


        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getCategories());

        Toast.makeText(this, "Press card item for edit, long press to remove item", Toast.LENGTH_LONG).show();


    }

    public void setRealmAdapter(RealmResults<Category> mCategory) {

        RealmCategoryAdapter realmAdapter = new RealmCategoryAdapter(this.getApplicationContext(), mCategory, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new CategoryAdapter(this);
        recycler.setAdapter(adapter);
    }


    private void fetchCategoriesItems() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Category>> call = apiService.getCategories(0);

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                      */
/*  mNoResults.setVisibility(View.VISIBLE);*//*

                    } else
                        setRealmData(response.body());
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


        private void setRealmData(List<Category> body) {

        List<Category> category_list = new ArrayList<>();
          //  Category category = new Category();


            for(int i = 0; i< body.size(); i++){
                Category category = new Category();
                System.out.println("!!!!!!!!!!!!"+ body.get(i));


                category.setDescription( body.get(i).getDescription());
                category.setSlug(body.get(i).getSlug());
                category.setName(body.get(i).getName());
                category_list.add(category);
                category.setId(body.get(i).getId());

            }




*/
/*
        category.setDescription("disccount applied");
        category.setSlug("Beauty and spa");
        category.setId(1);
        category_list.add(category);

        Category category2 = new Category();

        category.setDescription("haha working");
        category.setSlug("Service and spa");
        category.setId(2);*//*





        for (Category b : category_list) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(b);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);

    }
}
*/
