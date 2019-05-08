package techlab.digital.com.ecommclap.app;

import java.util.List;

import techlab.digital.com.ecommclap.model.imageSlider.ImageSliderResponse;

public class SingletonImagesList {

        private static SingletonImagesList instance;
        List<ImageSliderResponse> bannerModelList;

        private SingletonImagesList()
        {}

        public  static SingletonImagesList Instance()
        {
            if (instance == null)
            {
                instance = new SingletonImagesList();
            }
            return instance;
        }

        public List<ImageSliderResponse> getBannerModelList() {
            return bannerModelList;
        }

        public void setBannerModelList(List<ImageSliderResponse> mList) {
            this.bannerModelList = mList;
        }

    }

