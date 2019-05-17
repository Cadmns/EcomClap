package techlab.digital.com.ecommclap.fragments;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.AllServiceActivity;
import techlab.digital.com.ecommclap.activity.ScheduledParentsProductsActivity;
import techlab.digital.com.ecommclap.activity.schedule_products.FetchSchedulableSubCategory;
import techlab.digital.com.ecommclap.adapter.new_adapter.NewCategoriesAdapter;
import techlab.digital.com.ecommclap.app.Prefs;
import techlab.digital.com.ecommclap.app.SingletonImagesList;
import techlab.digital.com.ecommclap.model.fetch_category.Category;
import techlab.digital.com.ecommclap.model.imageSlider.ImageSliderResponse;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.services.services.FetchCategoryServiceOne;
import techlab.digital.com.ecommclap.utility.CheckInternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMainMenuFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,NewCategoriesAdapter.OnInterfaceListener {
    View view;
    Realm mRealm;
    RecyclerView mRecyclerView;
    NewCategoriesAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.main_screen)
    RelativeLayout mainScreenLayout;

    @BindView(R.id.internetConnection)
    RelativeLayout internetConnection;

    private long mLastClickTime = 0;

    @BindView(R.id.mshimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;
    List<CategoryRealmDb> list;
    ViewFlipper viewFlipper;
    String[] code;
    List<ImageSliderResponse> bannerModelList;
    BroadcastReceiver updateUIReciver;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.schdeule_banner)
    CardView schdeule_banner;
    @BindView(R.id.sports_banner)
    CardView sports_banner;

    public NewMainMenuFragment() {
        // Required empty public constructor
    }

    public static NewMainMenuFragment newInstance() {
        NewMainMenuFragment fragment = new NewMainMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_category, container, false);
        ButterKnife.bind(this, view);



        initViews();


        schdeule_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Intent intent = new Intent(getActivity(), FetchSchedulableSubCategory.class);
                startActivity(intent);
            }
        });


        sports_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryRealmDb categoryRealmDb = list.get(7);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext(), AllServiceActivity.class);
                bundle.putInt("object", categoryRealmDb.getId());
                bundle.putInt("view_pager", 7);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        return view;
    }

    private void initViews() {
        viewFlipper = (ViewFlipper) view.findViewById(R.id.vp_slider);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mShimmerViewContainer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        mRealm = Realm.getDefaultInstance();
        mRecyclerView = view.findViewById(R.id.recycler_category);
        mRecyclerView.setNestedScrollingEnabled(false);
        Prefs.with(getContext()).setPreLoad(true);
        fetchLocalCategoriescache();
        broadCasetRreceiver();


    }

    private void fetchLocalCategoriescache() {

        list = new ArrayList(mRealm.where(CategoryRealmDb.class).findAll());
        setAdapter(list);
        if (CheckInternet.isNetwork(getContext())) {
            ImageSlider();
            startService();
        } else if (!CheckInternet.isNetwork(getContext()) && list.isEmpty()){
            //do something, net is not connected
            Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
            noInternetMethod();
        }



    }

    @Override
    public void onDestroyView() {

        //  this.unregisterReceiver(updateUIReciver);
        super.onDestroyView();
    }
    IntentFilter filter;
    private void broadCasetRreceiver() {

        filter = new IntentFilter();
        filter.addAction("com.hello.action");
        updateUIReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);

                //UI update here
                mRealm = Realm.getDefaultInstance();
                ArrayList<CategoryRealmDb> list = new ArrayList(mRealm.where(CategoryRealmDb.class).findAll());

                updateRecyclerView(list);


            }

        };



    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).registerReceiver(updateUIReciver, filter);
    }


    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).unregisterReceiver(updateUIReciver);
    }

    public void ImageSlider() {
        if (SingletonImagesList.Instance().getBannerModelList() == null) {
            if (CheckInternet.isNetwork(getContext())) {
                fetchSliderImages();
            } else {
                //do something, net is not connected
                Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
            }
        } else {

            FetchImageList(SingletonImagesList.Instance().getBannerModelList());
        }
    }

    private void fetchSliderImages() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ImageSliderResponse>> call = apiService.fetchImageSlider();

        call.enqueue(new Callback<List<ImageSliderResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<ImageSliderResponse>> call, @NotNull Response<List<ImageSliderResponse>> response) {
                if (response.isSuccessful()) {
                    SingletonImagesList.Instance().setBannerModelList(response.body());
                    FetchImageList(response.body());
                } else {
                    Log.e("Error", "");
                }
            }

            @Override
            public void onFailure(Call<List<ImageSliderResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

                if (mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setAdapter(List<CategoryRealmDb> mFinalList) {
        mRecyclerView.setHasFixedSize(true);
        list = mFinalList;
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        //mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ((GridLayoutManager) mLayoutManager).setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewCategoriesAdapter(list, getContext());
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.mCallback = (NewCategoriesAdapter.OnInterfaceListener) this;


    }

    public void updateRecyclerView(List<CategoryRealmDb> mUpdatedList) {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.updateRecyclerViewInAdaprter(mUpdatedList);

    }

    int k;

    public void FetchImageList(List<ImageSliderResponse> mList) {
        bannerModelList = mList;
        code = new String[bannerModelList.size()];
        for (int i = 0; i < bannerModelList.size(); i++) {
            code[i] = bannerModelList.get(i).getSourceUrl();
        }
        for (String i : code) {
            forImageSlide(i);
        }
    }

    public void forImageSlide(String images) {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);

        if (getContext() == null) {
            Log.e("getContext is null", String.valueOf(getContext()));
        } else {
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

    @Override
    public void onItemsClick(View view, int position) {

        if (mCheckInternetWithMultipleClicks()) {

            CategoryRealmDb categoryRealmDb = list.get(position);
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getContext(), AllServiceActivity.class);
            bundle.putInt("object", categoryRealmDb.getId());
            bundle.putInt("view_pager", position);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);


        }
    }

    private Boolean mCheckInternetWithMultipleClicks() {

        if (CheckInternet.isNetwork(getContext())) {
            /* if (sessionManager.isLoggedIn()) {*/
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;
            //  }
        } else {
            //do something, net is not connected

            Toast.makeText(getContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

        }

        return false;

    }


    private void startService() {

        if (CheckInternet.isNetwork(Objects.requireNonNull(getContext()))) {
            try {
                Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), FetchCategoryServiceOne.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(getContext(), "Internet issued try again", Toast.LENGTH_SHORT);
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {

                                         if (CheckInternet.isNetwork(getContext())) {
                                             mSwipeRefreshLayout.setRefreshing(true);

                                             ImageSlider();
                                             startService();
                                             InternetFound();

                                         } else {
                                             Toast.makeText(getContext(), "Internet issued try again", Toast.LENGTH_SHORT);
                                             mSwipeRefreshLayout.setRefreshing(false);

                                             if (list.isEmpty()){
                                                 noInternetMethod();
                                             }


                                         }
                                     }

                                 }
        );


    }

    private void noInternetMethod(){

        mainScreenLayout.setVisibility(View.GONE);
        internetConnection.setVisibility(View.VISIBLE);
    }

    private void InternetFound(){

        mainScreenLayout.setVisibility(View.VISIBLE);
        internetConnection.setVisibility(View.GONE);
    }
}
