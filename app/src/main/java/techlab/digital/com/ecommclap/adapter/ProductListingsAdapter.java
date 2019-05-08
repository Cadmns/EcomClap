package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.ProductDescriptionActivity;
import techlab.digital.com.ecommclap.activity.ServiceDescriptionActivity;
import techlab.digital.com.ecommclap.model.fetchSubProducts.CustomVariations;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductVariationContainer;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.utility.CheckInternet;


public class ProductListingsAdapter
        extends RecyclerView.Adapter<ProductListingsAdapter.ViewHolder> {

    private List<ProductListingsModeResponse> datumList;
    Context context;
    Intent intent;

    // variable to track event time`
    private long mLastClickTime = 0;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView mItemName,mItemStockAvailablity,mItemPrice;
        public final ImageView mImageView;
        public final LinearLayout mLayoutItem;
        public final ImageView mImageViewWishlist;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.image1);
            mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
            mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
            mItemName =(TextView)view.findViewById(R.id.product_name);
            mItemPrice =(TextView)view.findViewById(R.id.item_price);
        }
    }

    public ProductListingsAdapter(Context context, List<ProductListingsModeResponse> items) {
        this.datumList = items;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_listings_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ProductListingsModeResponse datum = datumList.get(position);

        Glide.with(context).load(datum.getImages().get(0).getSrc())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);
        holder.mItemName.setText(datum.getName());
        holder.mItemPrice.setText("Rs : "+datum.getPrice());
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductListingsModeResponse productListingsModeResponse = datumList.get(position);
                ProductVariationContainer productVariationContainer = new ProductVariationContainer();

                if (!productListingsModeResponse.getVariations().isEmpty()) {


                    HashMap<String, CustomVariations> spinnerMap = new HashMap<>();

                    for (int k = 0; k <= productListingsModeResponse.getVariations().size(); k++) {
                        CustomVariations variations = new CustomVariations();


                        try {

                            variations.setId(productListingsModeResponse.getVariations().get(k).getId());
                            variations.setIn_stock(productListingsModeResponse.getVariations().get(k).getInStock());
                            variations.setWeight(productListingsModeResponse.getVariations().get(k).getWeight());
                            variations.setPrice(productListingsModeResponse.getVariations().get(k).getPrice());

                            for (int l = 0; l <= productListingsModeResponse.getAttributes().size(); l++) {

                                variations.setName(productListingsModeResponse.getVariations().get(k).getAttributes().get(l).getName());
                                variations.setOption(productListingsModeResponse.getVariations().get(k).getAttributes().get(l).getOption());
                                spinnerMap.put(productListingsModeResponse.getVariations().get(k).getPrice(), variations);
                        }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }





                        productVariationContainer.setVariations(variations);

                        productVariationContainer.setQuantity_attributes(spinnerMap);
                    }

                }
                else{

                    Log.e("TAG","no variation is provided");
                }



                if (CheckInternet.isNetwork(context)) {
                    /* if (sessionManager.isLoggedIn()) {*/
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return ;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if (productListingsModeResponse.getType().equals("phive_booking")){
                        intent = new Intent(context, ServiceDescriptionActivity.class);
                    }else {
                        intent = new Intent(context, ProductDescriptionActivity.class);
                    }
                    intent.putExtra("object", productListingsModeResponse);
                    intent.putExtra("variation",productVariationContainer);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    //  }
                }else {
                    //do something, net is not connected

                    Toast.makeText(context, "Connect to internet", Toast.LENGTH_SHORT).show();

                }





            }
        });

        //Set click action for wishlist
        holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addWishlistImageUri(mValues[position]);
                holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp);
                notifyDataSetChanged();
                Toast.makeText(mActivity,"Item added to wishlist.",Toast.LENGTH_SHORT).show();*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }
}