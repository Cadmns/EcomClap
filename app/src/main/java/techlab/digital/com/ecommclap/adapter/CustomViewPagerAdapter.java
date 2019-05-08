package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.imageSlider.ImageSliderResponse;

/**
 * Created by Dhruv on 9/15/2017.
 */

public class CustomViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ImageSliderResponse> bannerModelList;
    private LayoutInflater layoutInflater;

    public CustomViewPagerAdapter(Context context, List<ImageSliderResponse> bannerModelList) {
        this.context = context;
        this.bannerModelList = bannerModelList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bannerModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.slide, container, false);
        final ProgressBar progressBar = view.findViewById(R.id.homeprogress);
        ImageSliderResponse mBannerObject = bannerModelList.get(position);
        final ImageView sliderImage = view.findViewById(R.id.image);
        int radius = 30; // corner radius, higher Value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        //bind Value to the View Widgets
        Glide.with(context).load(mBannerObject.getGuid().getRendered())
             //   .bitmapTransform(new Rounded(context, radius, margin))
                .thumbnail(0.5f)
                .crossFade()
                .override(600, 200)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);

                        return false;
                    }
                })
                .skipMemoryCache(true)
                .into(sliderImage);


        container.addView(view);
        return view;
    }

}