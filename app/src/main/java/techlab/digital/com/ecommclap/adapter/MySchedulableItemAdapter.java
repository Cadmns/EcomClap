package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.schedule_products.SchedulableItemsActivity;
import techlab.digital.com.ecommclap.model.categories.subCategories.FetchSubCategory;
import techlab.digital.com.ecommclap.parse.SvgDecoder;
import techlab.digital.com.ecommclap.parse.SvgDrawableTranscoder;
import techlab.digital.com.ecommclap.parse.SvgSoftwareLayerSetter;
import techlab.digital.com.ecommclap.utility.CheckInternet;

public class MySchedulableItemAdapter extends RecyclerView.Adapter<MySchedulableItemAdapter.MyViewHolder>  {

    private Context mContext;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
    private List<FetchSubCategory> subCategories;
    RelativeLayout mrelativeLayout;
    // variable to track event time`
    private long mLastClickTime = 0;
    public MySchedulableItemAdapter(Context activity, List<FetchSubCategory> body) {
        this.mContext=activity;
        this.subCategories=body;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView mTitle;
        EditText mNoofItems;

        ImageView imageView;
        MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.images);
            mTitle = (TextView) view.findViewById(R.id.item_Name);
            mrelativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);


            /*Image Download */
            requestBuilder = Glide.with(mContext)
                    .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                    .from(Uri.class)
                    .as(SVG.class)
                    .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                    .sourceEncoder(new StreamEncoder())
                    .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                    .decoder(new SvgDecoder())
                    .placeholder(R.drawable.ic_email_id_svg)
                    .error(R.drawable.active_dot)
                    .animate(android.R.anim.fade_in)
                    .listener(new SvgSoftwareLayerSetter<Uri>());
        }


        private void loadNet(FetchSubCategory subCategory) {
            Uri uri = Uri.parse(subCategory.getImage().getSrc());
            requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(uri)
                    .into(imageView);


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FetchSubCategory subCategory = subCategories.get(position);

        holder.mTitle.setText(subCategory.getName());
        Log.e("imagesssssssssss",subCategory.getImage().getSrc());
        try {

            holder.loadNet(subCategory);
            Glide.with(mContext).load(subCategory.getImage().getSrc())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }catch (NullPointerException e){
            e.printStackTrace();
        }



        try{


        }catch (NullPointerException e){
            e.printStackTrace();
        }


        mrelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckInternet.isNetwork(mContext)) {
                    /* if (sessionManager.isLoggedIn()) {*/
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return ;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    Intent intent = new Intent(mContext, SchedulableItemsActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", subCategory.getId());
                    bundle.putString("category_name",subCategory.getName());
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    // }
                }else {
                    //do something, net is not connected

                    Toast.makeText(mContext, "Connect to internet", Toast.LENGTH_SHORT).show();

                }





            }
        });






        //  }
    }



    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SubCategoriesAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SubCategoriesAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
