package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.AllServiceActivity;
import techlab.digital.com.ecommclap.activity.ProductListings;
import techlab.digital.com.ecommclap.model.BannerModel;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;
import techlab.digital.com.ecommclap.parse.SvgDecoder;
import techlab.digital.com.ecommclap.parse.SvgDrawableTranscoder;
import techlab.digital.com.ecommclap.parse.SvgSoftwareLayerSetter;

public class BannerAdapter  extends  RecyclerView.Adapter<BannerAdapter.ViewAdapter> {


    List<BannerModel> m_List_category;
    Context mtx;

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
    private long mLastClickTime = 0;

    public BannerAdapter(List<BannerModel> list_category, Context context) {
        m_List_category = list_category;
        mtx = context;
    }


    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_list, parent, false);
        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAdapter holder, final int position) {

        final BannerModel mcategory = m_List_category.get(position);
        //holder.mcategory_text.setText(mcategory.getSlug());
        //check for slug new category...........
        holder.loadNet(mcategory, position);
         holder.bannerimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return ;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        /*Intent intent = new Intent(mtx, ProductListings.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", mcategory.getId());
        bundle.putString("category_name",mcategory.getName());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mtx.startActivity(intent);*/

        Bundle bundle = new Bundle();
        Intent intent = new Intent(mtx, AllServiceActivity.class);
        bundle.putInt("object",  m_List_category.get(position).getId());
        bundle.putInt("view_pager", m_List_category.get(position).getProduct_position());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mtx.startActivity(intent);



    }
});
       /* holder.m_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return ;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                //mCallback.onItemsClick(view,position);



            }
        });*/
    }

    @Override
    public int getItemCount() {
        return m_List_category.size();
    }

    class ViewAdapter extends RecyclerView.ViewHolder {

        ImageView bannerimage;

        ViewAdapter(View itemView) {
            super(itemView);

            bannerimage = itemView.findViewById(R.id.bannerimage);

            /*

             */
            /*Image Download *//*

            requestBuilder = Glide.with(mtx)
                    .using(Glide.buildStreamModelLoader(Uri.class, mtx), InputStream.class)
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

        private void loadNet(CategoryRealmDb categoryRealmDb, int position) {
            Uri uri = Uri.parse(categoryRealmDb.getImage());
            requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(uri)
                    .into(bannerimage);


        }

*/


        }

 /*   public void updateRecyclerViewBanner(List<CategoryRealmDb> mUpdatedList) {

        m_List_category = mUpdatedList;
        notifyDataSetChanged();
    }


    public interface OnInterfaceListener {
        void onItemsClick(View view, int position);

    }*/

        private void loadNet(BannerModel categoryRealmDb, int position) {
            /*Image Download */
            Glide.with(mtx)
                    .load(m_List_category.get(position).getImage())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(bannerimage);


        }
    }
}
