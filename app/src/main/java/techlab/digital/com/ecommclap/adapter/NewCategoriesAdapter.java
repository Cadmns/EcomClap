package techlab.digital.com.ecommclap.adapter.new_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.List;

import techlab.digital.com.ecommclap.R;

import techlab.digital.com.ecommclap.activity.ProductListings;
import techlab.digital.com.ecommclap.model.realmDbModel.CategoryRealmDb;
import techlab.digital.com.ecommclap.parse.SvgDecoder;
import techlab.digital.com.ecommclap.parse.SvgDrawableTranscoder;
import techlab.digital.com.ecommclap.parse.SvgSoftwareLayerSetter;

public class NewCategoriesAdapter extends RecyclerView.Adapter<NewCategoriesAdapter.ViewAdapter> {
  List<CategoryRealmDb> m_List_category;
  Context mtx;
  public OnInterfaceListener mCallback;
  private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

  public NewCategoriesAdapter(List<CategoryRealmDb> list_category, Context context) {
    m_List_category = list_category;
    mtx = context;
  }


      @NonNull
      @Override
      public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_category_list, parent, false);
        return new ViewAdapter(v);
      }

      @Override
      public void onBindViewHolder(@NonNull final ViewAdapter holder, final int position) {

        final CategoryRealmDb mcategory = m_List_category.get(position);
        holder.mcategory_text.setText(mcategory.getSlug());
        holder.loadNet(mcategory, position);

        holder.m_card_view.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

                  mCallback.onItemsClick(view,position);



          }
        });
      }

        @Override
        public int getItemCount() {
          return m_List_category.size();
        }

        class ViewAdapter extends RecyclerView.ViewHolder {
          TextView mcategory_text;
          ImageView mcategory_image;
          CardView m_card_view;
          ViewAdapter(View itemView) {
            super(itemView);
            m_card_view = itemView.findViewById(R.id.m_card_view);
            mcategory_text = itemView.findViewById(R.id.category_text);
            mcategory_image = itemView.findViewById(R.id.category_image);


            /*Image Download */
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
                    .into(mcategory_image);


          }

        }


        public void updateRecyclerViewInAdaprter(List<CategoryRealmDb> mUpdatedList) {

          m_List_category = mUpdatedList;
          notifyDataSetChanged();
        }


        public interface OnInterfaceListener {
          void onItemsClick(View view, int position);

        }
      }

