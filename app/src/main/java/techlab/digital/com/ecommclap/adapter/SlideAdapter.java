package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import techlab.digital.com.ecommclap.fragments.SlideFragment;
import techlab.digital.com.ecommclap.model.imageSlider.ImageSliderResponse;

/**
 * Created by Droid on 2/29/2016.
 */
public class SlideAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<ImageSliderResponse> bannerModelList;
    private int PAGES = 8;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    private int LOOPS = 1000;
    public int FIRST_PAGE = PAGES * LOOPS / 2;

    public SlideAdapter(FragmentManager fm) {
        super(fm);
    }

    public SlideAdapter(FragmentManager fm, List<ImageSliderResponse> bannerModelList) {
        super(fm);
        this.bannerModelList = bannerModelList;
    }

    @Override
    public Fragment getItem(int position) {
        position = position % PAGES;
        return SlideFragment.newInstance(Integer.toString(position + 1));
    }

    @Override
    public int getCount() {
        return PAGES * LOOPS;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }


}
