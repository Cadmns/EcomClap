package techlab.digital.com.ecommclap.fragments;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
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
import techlab.digital.com.ecommclap.activity.ProductListings;
import techlab.digital.com.ecommclap.activity.ScheduledParentsProductsActivity;
import techlab.digital.com.ecommclap.activity.schedule_products.FetchSchedulableSubCategory;

import techlab.digital.com.ecommclap.adapter.NewCategoriesAdapter;
import techlab.digital.com.ecommclap.app.Prefs;
import techlab.digital.com.ecommclap.app.SingletonImagesList;
import techlab.digital.com.ecommclap.model.fetch_category.Category;
import techlab.digital.com.ecommclap.model.imageSlider.ImageSliderResponse;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.parse.SvgDecoder;
import techlab.digital.com.ecommclap.parse.SvgDrawableTranscoder;
import techlab.digital.com.ecommclap.parse.SvgSoftwareLayerSetter;
import techlab.digital.com.ecommclap.services.services.FetchCategoryServiceOne;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMainMenuFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NewCategoriesAdapter.OnInterfaceListener {
    View view;
    Realm mRealm;
    RecyclerView mRecyclerView;
    NewCategoriesAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.main_screen)
    RelativeLayout mainScreenLayout;
    @BindView(R.id.internetConnection)
    RelativeLayout internetConnection;
int bannerId,position1=0;
String bannerName;
    private long mLastClickTime = 0;

    @BindView(R.id.mshimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;
    List<CategoryRealmDb> list;
    List<CategoryRealmDb> listclone = new ArrayList<>();
    ViewFlipper viewFlipper;
    String[] code;
    List<ImageSliderResponse> bannerModelList;
    BroadcastReceiver updateUIReciver;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
  //  @BindView(R.id.schdeule_banner)
    CardView schdeule_banner;
    @BindView(R.id.sports_banner)
    CardView sports_banner;
     TextView bannerslogen;
   // @BindView(R.id.veg)
    ImageView veg;
    SessionManager sessionManager;
    public NewMainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("this is on attach","hurrrrrah");

        ///getActivity().startService(new Intent(getActivity(), FetchCategoryServiceOne.class));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("this is on Dettach","purrrrrah");
    ///    getActivity().startService(new Intent(getActivity(), FetchCategoryServiceOne.class));
    }

    public static NewMainMenuFragment newInstance() {
        NewMainMenuFragment fragment = new NewMainMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the highlight_remove for this fragment
        view = inflater.inflate(R.layout.new_fragemt_catolist, container, false);
        ButterKnife.bind(this, view);
        veg=view.findViewById(R.id.veg);
        schdeule_banner=view.findViewById(R.id.schdeule_banner);
        sessionManager = new SessionManager(getContext());
        bannerslogen=view.findViewById(R.id.bannerSlogan);
        initViews();

veg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.e("id on click", "veg**** ");
                Log.e("id on click", String.valueOf(bannerId));
                Log.e("id on  && name", String.valueOf(bannerName));
             /*   Intent intent = new Intent(getContext(), ProductListings.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", bannerId);
                bundle.putString("category_name",bannerName);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
*/

       // CategoryRealmDb categoryRealmDb = list.get(position1);
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), AllServiceActivity.class);
        bundle.putInt("object", bannerId);
        bundle.putInt("view_pager", position1);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
});

           /* schdeule_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<list.size();i++) {
                        if(list.get(i).getSlug().equals("meatbanner")) {

                            CategoryRealmDb categoryRealmDb = list.get(i);
                            Log.e("id on click", String.valueOf( categoryRealmDb.getId()));
                            Log.e("id on  && name", String.valueOf(categoryRealmDb.getName()));
                            Intent intent = new Intent(getContext(), ProductListings.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", categoryRealmDb.getId());
                            bundle.putString("category_name",categoryRealmDb.getName());
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                   *//* Intent intent = new Intent(getActivity(), FetchSchedulableSubCategory.class);
                    startActivity(intent);*//*
                }
            });*/


            sports_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Log.e("id on click", "sport bvanner");

                    /*
                    CategoryRealmDb categoryRealmDb = list.get(7);
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getContext(), AllServiceActivity.class);
                    bundle.putInt("object", categoryRealmDb.getId());
                    bundle.putInt("view_pager", 7);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);*/
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
            @SuppressLint("LongLogTag")
            @Override
            public void onReceive(Context context, Intent intent) {
                if (mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);

                //UI update here
                mRealm = Realm.getDefaultInstance();
                ArrayList<CategoryRealmDb> list = new ArrayList(mRealm.where(CategoryRealmDb.class).findAll());



                Log.e("size of listlistlist all", String.valueOf(list.size()));

                String location_desc = sessionManager.getKeySelectCityDescrption();

                String[] data_array = location_desc.split("\\,");



                for(int i=0;i<list.size();i++)
                {
                    for(int j=0;j<data_array.length;j++)
                    {


                        if(list.get(i).getSlug().equals(data_array[j]))
                        {
                            if(list.get(i).getSlug().equals("meatbanner"))
                            {
                                Log.e("DESCRIPTION####",list.get(i).getDescription());
                                Log.e("imageurl realm",list.get(i).getName());
                                Log.e("id+4444", String.valueOf(list.get(i).getId()));
                                bannerId=Integer.parseInt(String.valueOf(list.get(i).getId()));
                                bannerName= String.valueOf(list.get(i).getName());
                                position1=j;
                                veg.setVisibility(View.VISIBLE);
                                schdeule_banner.setVisibility(View.VISIBLE);
                                sports_banner.setVisibility(View.VISIBLE);
                                bannerslogen.setVisibility(View.VISIBLE);
                                Log.e("positoin", String.valueOf(j));
                                Log.e("positoin", String.valueOf(position1));
                                /*Image Download */
                                Glide.with(NewMainMenuFragment.this)
                                        .load(list.get(i).getImage())
                                        .centerCrop()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .dontAnimate()
                                        .into(veg);
                            }
                            else{
                                if (!listclone.contains(list.get(i)))
                                    listclone.add(list.get(i));
                                Log.e("Added item", String.valueOf(list.get(i)));
                                break;
                            }
                   /* if(!listclone.contains(mFinalList.get(i)))
                     listclone.add(mFinalList.get(i));

                    Log.e("Added item", String.valueOf(mFinalList.get(i)));
                    break;*/
                        }


                       /* Log.e("dataarrry",data_array[j]);
                        if(list.get(i).getSlug().equals(data_array[j]))
                        {
                            if(!listclone.contains(list.get(i))){
                                listclone.add(list.get(i));
                            }
                            Log.e("Added item", String.valueOf(list.get(i)));
                            break;
                        }*/

                    }


                  /*  if(list.get(i).getSlug().equals("fruits-vegetables") || list.get(i).getSlug().equals("beauty-spa") ||list.get(i).getSlug().equals("breakfast-need") ){
                        listclone.add(list.get(i));
                    }*/
                }


                updateRecyclerView(listclone);








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
        if (mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.destroyDrawingCache();
            mSwipeRefreshLayout.clearAnimation();
        }
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
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
    private void setAdapter(List<CategoryRealmDb> mFinalList) {

        Log.e("size of db all", String.valueOf(mFinalList.size()));

        String location_desc = sessionManager.getKeySelectCityDescrption();

        String[] data_array = location_desc.split("\\,");



        for(int i=0;i<mFinalList.size();i++)
        {
            for(int j=0;j<data_array.length;j++)
            {
                Log.e("dataarrry",data_array[j]);
                if(mFinalList.get(i).getSlug().equals(data_array[j]))
                {
                    if(mFinalList.get(i).getSlug().equals("meatbanner"))
                    {

                        Log.e("DESCRIPTION####",mFinalList.get(i).getDescription());
                        Log.e("imageurl###",mFinalList.get(i).getImage());
                        Log.e("id+4444", String.valueOf(mFinalList.get(i).getId()));
                        bannerId=Integer.parseInt(String.valueOf(list.get(i).getId()));
                        bannerName= String.valueOf(list.get(i).getName());
                        position1=j;
                        veg.setVisibility(View.VISIBLE);
                        schdeule_banner.setVisibility(View.VISIBLE);
                        sports_banner.setVisibility(View.VISIBLE);
                        bannerslogen.setVisibility(View.VISIBLE);
                        /*Image Download */
                        Glide.with(NewMainMenuFragment.this)
                                .load(mFinalList.get(i).getImage())
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .dontAnimate()
                                .into(veg);

                    }
                    else{
                       if (!listclone.contains(mFinalList.get(i)))
                        listclone.add(mFinalList.get(i));
                        Log.e("Added item", String.valueOf(mFinalList.get(i)));
                        break;
                    }
                   /* if(!listclone.contains(mFinalList.get(i)))
                     listclone.add(mFinalList.get(i));

                    Log.e("Added item", String.valueOf(mFinalList.get(i)));
                    break;*/
                }

            }

           /* if(list.get(i).getSlug().equals("fruits-vegetables") || list.get(i).getSlug().equals("beauty-spa") ||list.get(i).getSlug().equals("breakfast-need") ){
                listclone.add(list.get(i));
            }*/
        }



        mRecyclerView.setHasFixedSize(true);
        list =listclone;
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        //mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ((GridLayoutManager) mLayoutManager).setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewCategoriesAdapter(list, getContext());
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.mCallback = (NewCategoriesAdapter.OnInterfaceListener) this;


    }


    /*private void loadNet(CategoryRealmDb categoryRealmDb, int position) {
        Uri uri = Uri.parse(categoryRealmDb.getImage());
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(sports_banner);
    }*/

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
                                         ImageSlider();
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
