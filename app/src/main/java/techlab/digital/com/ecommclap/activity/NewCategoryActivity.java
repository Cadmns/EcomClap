package techlab.digital.com.ecommclap.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;

import techlab.digital.com.ecommclap.app.Prefs;
import techlab.digital.com.ecommclap.app.SingletonImagesList;
import techlab.digital.com.ecommclap.fragments.MainMenuFragment;
import techlab.digital.com.ecommclap.fragments.MyOffersFragments;
import techlab.digital.com.ecommclap.fragments.NewMainMenuFragment;
import techlab.digital.com.ecommclap.model.fetch_category.Category;
import techlab.digital.com.ecommclap.model.imageSlider.ImageSliderResponse;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.notification.NotificationCountSetClass;
import techlab.digital.com.ecommclap.services.services.FetchCategoryServiceOne;
import techlab.digital.com.ecommclap.utility.BottomNavigationViewHelper;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class NewCategoryActivity extends AppCompatActivity{
    @BindView(R.id.toolbarTitle)
    TextView toolbar_title;
    SessionManager sessionManager;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    /*view flipper*/
    private long mLastClickTime = 0;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    ProgressDialog progressDialog;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        initViews();
        requestOtpReadpermission();


    }

    private void requestOtpReadpermission(){

        if (ContextCompat.checkSelfPermission(NewCategoryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewCategoryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
    }

    private void initViews(){

        sessionManager= new SessionManager(getApplicationContext());
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        loadFragment(NewMainMenuFragment.newInstance());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar_title.setText(R.string.app_name);


        if (CheckInternet.isNetwork(getApplicationContext())) {
            if (sessionManager.isLoggedIn() && sessionManager.getUserId() != 0) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                fetchUserDetails();
            }
        }else {
            // do something, net is not connected
            Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar_title.setText(R.string.app_name);

                    if (CheckInternet.isNetwork(getApplicationContext())) {
                        loadFragment(NewMainMenuFragment.newInstance());
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

                    }
                    return true;

                case R.id.navigation_offers:
                    if (CheckInternet.isNetwork(getApplicationContext())) {
                        toolbar_title.setText(R.string.offers);

                        loadFragment(MyOffersFragments.newInstance());
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

                    }
                    return true;

                case R.id.navigation_ecommclap:

                    if (CheckInternet.isNetwork(getApplicationContext())) {
                        Intent intent2 = new Intent(getApplicationContext(), AppInformationActivity.class);
                        startActivity(intent2);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

                    }
                    return true;

                case R.id.navigation_user_profile:
                    if (CheckInternet.isNetwork(getApplicationContext())) {
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_cart);
        NotificationCountSetClass.setAddToCart(NewCategoryActivity.this, item,notificationCountCart);
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }
    public static int notificationCountCart = 0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_cart) {

            startActivity(new Intent(NewCategoryActivity.this, CartActivity.class));

            return true;
        }else {
            startActivity(new Intent(NewCategoryActivity.this, UserProfileActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment selectedFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
    }

    public void onBackPressed() {

        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

        } else {

            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();


        }

        back_pressed = System.currentTimeMillis();
        //finish();
        // super.onBackPressed();
    }

    private void fetchUserDetails(){


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserDetailsResponse> call = apiService.fetchUserDetails(sessionManager.getUserId(),"Bearer " + sessionManager.getKeySession());

        call.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                if(response.isSuccessful()){
                    storeUserInfoSharedPref(getApplicationContext(),response.body());
                }else{
                    Log.e("Error","");
                    if (response.code() == 403){


                        //  initSnackBar("Incorrect password");


                    }
                    //  finish();
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());

            }
        });
    }


    public static final String PREFS_NAME = "EcommClap";

    // used for store arrayList in json format
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private void storeUserInfoSharedPref(Context context, UserDetailsResponse finalContestModel){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(finalContestModel);
        prefsEditor.putString("user_info", json);
        prefsEditor.apply();
    }

}
