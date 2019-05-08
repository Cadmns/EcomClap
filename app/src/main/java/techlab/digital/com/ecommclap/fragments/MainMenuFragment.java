package techlab.digital.com.ecommclap.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.AllServiceActivity;
import techlab.digital.com.ecommclap.adapter.CustomViewPagerAdapter;
import techlab.digital.com.ecommclap.adapter.SlideAdapter;
import techlab.digital.com.ecommclap.app.SingletonImagesList;
import techlab.digital.com.ecommclap.model.imageSlider.ImageSliderResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {
    View view;

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private int dotsCount;
    private ImageView[] dots;
   /* @BindView(R.id.pager)
    public ViewPager viewPager;*/
    private CustomViewPagerAdapter mAdapter;
    @BindView(R.id.sliderDots)
    LinearLayout sliderDotsPanel;
    @BindView(R.id.beauty_spa)
    CardView mBeauty_spa;
    @BindView(R.id.home_service)
    CardView mHomeService;
    @BindView(R.id.medical_health)
    CardView mMedical_health;
    @BindView(R.id.fruit_vegetables)
    CardView mFruits_vegetables;
    @BindView(R.id.food)
    CardView mFood;
    @BindView(R.id.breakfast_needs)
    CardView mBreakFastNeeds;
    @BindView(R.id.sports_goods)
    CardView mSportsGoods;
    @BindView(R.id.tutors)
    CardView mTutors;
    @BindView(R.id.exploreMore)
    CardView mExpolre_more;
    SessionManager sessionManager;
    @BindView(R.id.shimmer_view_container3)
    ShimmerFrameLayout mShimmerViewContainer;
  /*view flipper*/
    ViewFlipper viewFlipper;
    List<ImageSliderResponse> bannerModelList;
    String[] code;
    // variable to track event time`
    ViewPager viewPager2;
    SlideAdapter adapter;
    private long mLastClickTime = 0;
    public MainMenuFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_main_menu, container, false);
        ButterKnife.bind(this,view);
        sessionManager = new SessionManager(getContext());
        /*slider pageView*/
        viewFlipper=(ViewFlipper) view.findViewById(R.id.vp_slider);
        viewFlipper.removeView(view);
       // setDaaptersetupViewPager();

    //    initViews();
        ImageSlider();
        return  view;
     }
    public void ImageSlider()
    {
        if(SingletonImagesList.Instance().getBannerModelList()==null){
            if (CheckInternet.isNetwork(getContext())) {
            fetchSliderImages();

        }else {
            //do something, net is not connected
            Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
        }

        }
        else{
            FetchImageList(SingletonImagesList.Instance().getBannerModelList());
        }
    }
    int k;
    public void FetchImageList(List<ImageSliderResponse> mList)
    {
        bannerModelList=mList;
        code = new String[bannerModelList.size()];
        for (int i = 0; i < bannerModelList.size(); i++) {
            code[i] = bannerModelList.get(i).getSourceUrl();
        }
        Log.e("arry Data", String.valueOf(code));

        for(String i:code)
        {
            forImageSlide(i);
        }
    }


    public void forImageSlide(String images) {
        if (getContext() == null) {
            Log.e("getContext is null", String.valueOf(getContext()));
        }
        else {
            ImageView imageView = new ImageView(getContext());
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            try {
                Glide.with(this)
                        .load(images) // image url
                        .centerCrop()
                        .into(imageView);  // imageview object
//    Glide.with(this).load(images).into(imageView);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(4000);
            viewFlipper.setAutoStart(true);
            viewFlipper.startFlipping();
            viewFlipper.setInAnimation(getContext(), android.R.anim.slide_out_right);
            viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_in_left);
        }

    }


     public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        return fragment;
    }


    @OnClick(R.id.beauty_spa)
    public void beautySpa(View view) {
        // TODO submit data to server...

      callAllServiceActivity(0);

    }
    @OnClick(R.id.home_service)
    public void homeService(View view) {
        // TODO submit data to server...
        callAllServiceActivity(1);
    }
    @OnClick(R.id.medical_health)
    public void medicalhealth(View view) {
        // TODO submit data to server...
        callAllServiceActivity(2);
    }
    @OnClick(R.id.fruit_vegetables)
    public void fruitVegetables(View view) {
        // TODO submit data to server...
        callAllServiceActivity(3);
    }
    @OnClick(R.id.food)
    public void food(View view) {
        // TODO submit data to server...
        callAllServiceActivity(4);
    }
    @OnClick(R.id.breakfast_needs)
    public void breakfastNeeds(View view) {
        // TODO submit data to server...
        callAllServiceActivity(5);
    }
    @OnClick(R.id.sports_goods)
    public void sportsgoods(View view) {
        // TODO submit data to server...
        callAllServiceActivity(6);
    }
    @OnClick(R.id.tutors)
    public void tutors(View view) {
        // TODO submit data to server...
        callAllServiceActivity(7);
    }
    @OnClick(R.id.exploreMore)
    public void exporeMore(View view) {
        // TODO submit data to server...
        callAllServiceActivity(8);
    }



    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();

    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }


    private void fetchSliderImages(){

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ImageSliderResponse>> call = apiService.fetchImageSlider();

        call.enqueue(new Callback<List<ImageSliderResponse>>() {
            @Override
            public void onResponse(Call<List<ImageSliderResponse>> call, Response<List<ImageSliderResponse>> response) {
                if(response.isSuccessful()){
                    SingletonImagesList.Instance().setBannerModelList(response.body());
                    FetchImageList(response.body());
                  }else{
                    Log.e("Error","");
                }
            }

            @Override
            public void onFailure(Call<List<ImageSliderResponse>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
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

    private void callAllServiceActivity(int viewPagerPosition){
        if (mCheckInternetWithMultipleClicks()){
            Intent intent = new Intent(getContext(), AllServiceActivity.class);
            intent.putExtra("view_pager", viewPagerPosition);
            startActivity(intent);
        }

    }




}
