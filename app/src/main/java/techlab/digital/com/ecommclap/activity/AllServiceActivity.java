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
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.realm.Realm;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.fragments.ImageListFragment;
import techlab.digital.com.ecommclap.model.categories.subCategories.mainCategories.MainCategory;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;


public class AllServiceActivity extends AppCompatActivity implements  ImageListFragment.onSubCategry_notFound {
    public static int notificationCountCart = 0;
    static ViewPager viewPager;
    static TabLayout tabLayout;
    List<MainCategory> categories;
    int viewPagerPosition;
    CategoryRealmDb categoryRealmDb;
    int item_position;
    Realm mRealm;
    ArrayList<CategoryRealmDb> mCategoriesDbList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_service);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
                Bundle bundle = getIntent().getExtras();
                viewPagerPosition = bundle.getInt("view_pager");
                item_position = bundle.getInt("object");
                init();

        }else {
            // Do something else
            Toast.makeText(getApplicationContext(), "Unexpected issue", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    /*fetch categories data from the realm database*/
    private ArrayList<CategoryRealmDb> fetchCategoriesDataFromRealmDb(){

       return new ArrayList<>(mRealm.where(CategoryRealmDb.class).findAll());

    }

    private void init(){
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

    private void populateCategories(){

        mCategoriesDbList=fetchCategoriesDataFromRealmDb();

        mCategoriesDbList = new ArrayList(mRealm.where(CategoryRealmDb.class).findAll());
        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
    private void setupViewPager(ViewPager viewPager){

        if (mCategoriesDbList != null && mCategoriesDbList.size()>0) {
            Adapter adapter = new Adapter(getSupportFragmentManager());
            for (CategoryRealmDb categories : mCategoriesDbList) {

                ImageListFragment fragment = new ImageListFragment();
                Bundle bundle = new Bundle();

                bundle.putInt("type", categories.getId());
                bundle.putString("eta",categories.getDescription());

                fragment.setArguments(bundle);
                adapter.addFragment(fragment, categories.getSlug());


            }
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapter);


            viewPager.setCurrentItem(viewPagerPosition);
        }
    }

    @Override
    public void on_product_found(String id, String name) {
        Log.e("TAG",id+name+"______hurrrah");
        Adapter adapter = new Adapter(getSupportFragmentManager());

       /* ProductListingFragment fragment = new ProductListingFragment();
        Bundle bundle = new Bundle();

        bundle.putInt("type", 2);
        bundle.putString("eta",name);
        adapter.addFragment(fragment, "");
        */
        /*ProductListingFragment fragment = new ProductListingFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content,fragment,"");
        transaction.addToBackStack(null);
        transaction.commit();
*/

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

}
