package techlab.digital.com.ecommclap.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.ProductListingsAdapter;
import techlab.digital.com.ecommclap.fragments.AddNewOrRepeatBottomSheet;
import techlab.digital.com.ecommclap.fragments.ImageListFragment;
import techlab.digital.com.ecommclap.fragments.ProductListingFragment;
import techlab.digital.com.ecommclap.fragments.ProductVariationsSheet;
import techlab.digital.com.ecommclap.fragments.UpdateQuantityOnlySheet;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartReq;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartResponse;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartWithVariationReq;
import techlab.digital.com.ecommclap.model.categories.subCategories.mainCategories.MainCategory;
import techlab.digital.com.ecommclap.model.fetchSubProducts.CustomVariations;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductVariationContainer;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;


public class AllServiceActivity extends AppCompatActivity implements ImageListFragment.onSubCatNotFoundCallBack , UpdateQuantityOnlySheet.onQuantityUpdateOnly,AddNewOrRepeatBottomSheet.onOptionSelected, ProductVariationsSheet.onVariationChanged{
    public static int notificationCountCart = 0;
    static ViewPager viewPager;
    static TabLayout tabLayout;
    List<MainCategory> categories;
    int viewPagerPosition;
    CategoryRealmDb categoryRealmDb;
    int item_position;
    Realm mRealm;
    ArrayList<CategoryRealmDb> mCategoriesDbList;
    String p_variation,p_quantity;
    SessionManager sessionManager;
    ProductListingsAdapter my_product_adpater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_service);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        sessionManager = new SessionManager(getApplicationContext());
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bundle bundle = getIntent().getExtras();
            viewPagerPosition = bundle.getInt("view_pager");
            item_position = bundle.getInt("object");
            init();
        } else {
            // Do something else
            Toast.makeText(getApplicationContext(), "Unexpected issue", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    /*fetch categories data from the realm database*/
    private ArrayList<CategoryRealmDb> fetchCategoriesDataFromRealmDb() {
        return new ArrayList<>(mRealm.where(CategoryRealmDb.class).findAll());
    }

    private void init() {
        categories = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.all_service_));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        populateCategories();
    }

    private void populateCategories() {
        mCategoriesDbList = fetchCategoriesDataFromRealmDb();
        mCategoriesDbList = new ArrayList(mRealm.where(CategoryRealmDb.class).findAll());
        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        if (mCategoriesDbList != null && mCategoriesDbList.size() > 0) {
            Adapter adapter = new Adapter(getSupportFragmentManager());
            for (CategoryRealmDb categories : mCategoriesDbList) {
                ImageListFragment fragment = new ImageListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", categories.getId());
                bundle.putString("eta", categories.getDescription());
                bundle.putString("cat_slug",categories.getSlug());

                Log.e("cat_slug",categories.getSlug());
                fragment.setArguments(bundle);
                adapter.addFragment(fragment, categories.getSlug());
            }
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(viewPagerPosition);
        }
    }

    @Override
    public void on_product_found(ProductListingsAdapter adapter) {
        my_product_adpater = adapter;
    }

    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
            notifyDataSetChanged();

        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void change_variation_is(String variation, String quantity, ProductListingsModeResponse add_cart_product_response, int product_position, ProductListingsAdapter.ViewHolder holder) {
        int updateIndex = product_position;
        my_product_adpater.update_elegent(updateIndex,quantity,holder);
        p_variation = variation;
        p_quantity = quantity;
        addtoCartProduct(add_cart_product_response);
    }
    /*Interface caLLBack to get User Choice wheather he wants to add new product or repeat last one*/
    @Override
    public void selected_option_is(String option, ProductListingsModeResponse m_data_object, int data_position, String number_quantity, ProductListingsAdapter.ViewHolder holder) {
        if(option.equals("add"))
        {
            try {
                ProductVariationContainer m_variation_container = is_having_variations(m_data_object);
                if (!m_variation_container.equals(null)) {
                    ProductVariationsSheet fragmentEliminaPost = new ProductVariationsSheet(getApplicationContext(), m_data_object, data_position, number_quantity, holder);
                    fragmentEliminaPost.show(getSupportFragmentManager(), "ahahahaha");
                    fragmentEliminaPost.setCancelable(false);
                } else
                    Log.e("TAG","no variation is provided");
            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }

        }
        else if(option.equals("repeat")){
            UpdateQuantityOnlySheet fragmentEliminaPost = new UpdateQuantityOnlySheet(getApplicationContext(),m_data_object,number_quantity,data_position,holder);
            fragmentEliminaPost.show(getSupportFragmentManager(), "ahahahaha");
            fragmentEliminaPost.setCancelable(false);
        }
    }
    /*interface call back to update the quantity only */
    @Override
    public void update_quantity_only(String quantity, ProductListingsModeResponse object_data, int position, ProductListingsAdapter.ViewHolder my_holder) {
        my_product_adpater.update_elegent(position,quantity,my_holder);
        updateQuantity(object_data,quantity);
        Toast.makeText(getApplicationContext(),quantity,Toast.LENGTH_SHORT).show();
    }






    /**********************************Network Calling methdsto communicate with server*************************************/

    /*network calling to add to cart to server and app ass well*/
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
                    Toast.makeText(getApplicationContext(), "Added to cart success", Toast.LENGTH_SHORT).show();
                    Log.e("response boody cp ", String.valueOf(response.body().getKey()));
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

    /*this bunch of code is the method to test product is having varition or not*/
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
    /*network calling to update the quantiity*/
    private void updateQuantity(ProductListingsModeResponse response,String quantity){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.updateQuantity("Bearer " + sessionManager.getKeySession(),response.getmRefrenceKey(),quantity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Item Updated Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("Error","");
                    if (response.code() == 403){
                        /*do call back  for forbidden erorr*/
                    }
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

            }
        });


    }



}
