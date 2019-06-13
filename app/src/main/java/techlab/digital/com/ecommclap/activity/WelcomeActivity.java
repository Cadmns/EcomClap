package techlab.digital.com.ecommclap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.app.Prefs;
import techlab.digital.com.ecommclap.model.fetch_category.Category;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.services.services.FetchCategoryServiceOne;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class WelcomeActivity extends AppCompatActivity  {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnNext,fb_login;
    SessionManager sessionManager;
    Realm mRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // Checking for first time launch - before calling setContentView()
       // prefManager = new PrefManager(this);
        sessionManager =new SessionManager(getApplicationContext());
        if (!sessionManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        check_realm_database();
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
       /* btnSkip = (Button) findViewById(R.id.btn_skip);*/
        btnNext = (Button) findViewById(R.id.btn_next);
        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }




    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        sessionManager.setFirstTimeLaunch(false);
        if (!sessionManager.isNumberVerified()){
        //   prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(WelcomeActivity.this, UserAuthentication.class));
        }else {
          if (sessionManager.getUserCity().equals(" ")){
                Intent intent = new Intent(getApplicationContext(), SelectCityActivity.class);
                Bundle bundles = new Bundle();
                bundles.putString("edttext", "FromStarting");
                intent.putExtras(bundles);
                startActivity(intent);
            }else {
                startActivity(new Intent(WelcomeActivity.this, NewCategoryActivity.class));

            }


        }
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText("GOT IT");
             //   btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText("Next");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {
        }

        @NotNull
        @Override
        public Object instantiateItem(@NotNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NotNull View view, @NotNull Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }




    public void check_realm_database() {
        mRealm = Realm.getDefaultInstance();
        RealmResults<CategoryRealmDb> results = mRealm.where(CategoryRealmDb.class).findAll();
        if (results.size() <= 0) {
            fetchCategoriesItems_and_insert();

        } else {
         //   Toast.makeText(getApplicationContext(), "data is already insetred", Toast.LENGTH_SHORT).show();
         /* *//*  Intent i = new Intent(getApplicationContext(), NewCategoryActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);*//*
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);*/



            launchHomeScreen();
        }

    }


    ////setting data in realm databse *///////////////

    private void fetchCategoriesItems_and_insert() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Category>> call = apiService.getCategories(0,50);

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        /*  mNoResults.setVisibility(View.VISIBLE);*/
                    }
                    else{
                        setRealmData(response.body());
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



    private void setRealmData(List<Category> body) {
        if(body.get(0).getSlug().equals("uncategorised")){
            body.remove(0);
        }

        List<CategoryRealmDb> category_list = new ArrayList<>();

        for(int i = 0; i< body.size(); i++){
            CategoryRealmDb category = new CategoryRealmDb();

            category.setDescription( body.get(i).getDescription());
            category.setSlug(body.get(i).getSlug());
            category.setName(body.get(i).getName());
            category.setId(body.get(i).getId());
            try {
                if (body.get(i).getImage().getSrc().equals("")) {

                } else {
                    category.setImage(body.get(i).getImage().getSrc());
                }
            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }
            category_list.add(category);

        }
        for (CategoryRealmDb b : category_list) {
            // Persist your data easily
            mRealm.beginTransaction();
            mRealm.copyToRealm(b);
            mRealm.commitTransaction();

        }
        Prefs.with(this).setPreLoad(true);

    }


}