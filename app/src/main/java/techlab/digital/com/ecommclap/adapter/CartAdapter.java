package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.cartModel.FetechCart.FetchCartResponse;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<FetchCartResponse> mProducts;
    private Context mContext;
    public OnInterfaceListener mCallback;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView mTitle,mPrice,delvieryCharges,mRemove;
        EditText mNoofItems;
        ElegantNumberButton quantity;
        LinearLayout mDeleteProduct;
        ImageView frameLayout;
        TextView mProductPrice;
        MyViewHolder(View view) {
            super(view);
            frameLayout = (ImageView) view.findViewById(R.id.image_cartlist);
            mTitle = (TextView) view.findViewById(R.id.item_Name);
            mProductPrice = (TextView) view.findViewById(R.id.priceValue);
            delvieryCharges = (TextView) view.findViewById(R.id.deliveryCharges);
            mRemove = (TextView) view.findViewById(R.id.remove);
            quantity = (ElegantNumberButton)view. findViewById(R.id.quantity1);
            mDeleteProduct =(LinearLayout)view.findViewById(R.id.layout_action1);
            quantity.setRange(1,100);

        }






    }






    public void removeItem(LinearLayout layout_cart_empty,LinearLayout layoutCartPayments) {

      /*  try {
            mProducts.remove(position);
            // notify the item removed by position
            // to perform recycler view delete animations
            // NOTE: don't call notifyDataSetChanged()
            notifyItemRemoved(position);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }*/
        if(mProducts.size()==0)
        {

            layout_cart_empty.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.GONE);
        }


      //  mCallback.onRemoveAck(mProducts);
    }


    public CartAdapter(Context context, List<FetchCartResponse> mProducts) {
        mContext = context;
        this.mProducts = mProducts;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        FetchCartResponse fetchCartResponse = mProducts.get(position);
        try{
        Glide.with(mContext).load(fetchCartResponse.getProductImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.frameLayout);
            holder.mTitle.setText(fetchCartResponse.getProductName());
            holder.quantity.setNumber(String.valueOf(fetchCartResponse.getQuantity()));
            holder.mProductPrice.setText("Rs : "+String.valueOf(fetchCartResponse.getLineTotal()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }




        holder.mDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mCallback.onRemoveItemsClick(view,holder.getAdapterPosition());


                FetchCartResponse fetchCartRespons = mProducts.get(position);
                String  itemLabel = String.valueOf(mProducts.get(position));
                mProducts.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mProducts.size());
                mCallback.onRemoveItemsClick2((fetchCartRespons.getKey()));
               // Toast.makeText(mContext," Item adapter Removed ",Toast.LENGTH_SHORT).show();

            }
        });



        holder.quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                                                             @Override
                                                             public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                                                                 if (newValue !=0)
                                                                     mCallback.onUpdateItemsClick(view,position,newValue);
                                                                 else
                                                                     Toast.makeText(mContext,"Quantity must be minimum 1",Toast.LENGTH_SHORT).show();

                                                             }
                                                         });


    }




    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public interface OnInterfaceListener {
        void onRemoveItemsClick(View view, int position);
        void onRemoveItemsClick2(String  key);

        void onUpdateItemsClick(View view, int position, int quantity);

      //  void onRemoveAck(List<FetchCartResponse> mProducts);
    }
}