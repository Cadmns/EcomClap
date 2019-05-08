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

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.VirtualCartSingleton;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponsetTwo;

public class ScheduleCartAdapter extends RecyclerView.Adapter<ScheduleCartAdapter.MyViewHolder>{

    VirtualCartSingleton temp = VirtualCartSingleton.getInstance( );
    private List<ProductListingsModeResponsetTwo> mProducts;
    private Context mContext;
    public InterfaceListener mCallback;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView mTitle,mPrice,delvieryCharges,mRemove;
        EditText mNoofItems;
        TextView quantity;
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
            quantity =(TextView)view.findViewById(R.id.quantity1);
            mDeleteProduct =(LinearLayout)view.findViewById(R.id.layout_action1);
        }
    }

    public ScheduleCartAdapter(Context context, List<ProductListingsModeResponsetTwo> mProducts) {
        mContext = context;
        this.mProducts = mProducts;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_products_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ProductListingsModeResponsetTwo fetchCartResponse = mProducts.get(position);
        holder.mTitle.setText(fetchCartResponse.getName());
        holder.mTitle.setText(fetchCartResponse.getName());
        holder.quantity.setText(String.valueOf(fetchCartResponse.getQuantity()));
        holder.mProductPrice.setText("Rs : "+String.valueOf(fetchCartResponse.getPrice()));
               Glide.with(mContext)
                .load(fetchCartResponse.getImages().get(0).getSrc()) // Remote URL of image.
                .into(holder.frameLayout);
         holder.mRemove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ProductListingsModeResponsetTwo fetchCartRespons = mProducts.get(position);
        String  itemLabel = String.valueOf(mProducts.get(position));
        mProducts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mProducts.size());
        mCallback.onRemoveItemClick(position,(fetchCartRespons.getId()),temp.getmFinalList_for_cart().size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
    public interface InterfaceListener
    {
        void onRemoveItemClick(int position,int id,int size_of_list);
    }

    public void removeItem(int position) {
       try {
            mProducts.remove(position);
            notifyItemRemoved(position);
           notifyItemRangeChanged(position,mProducts.size());
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

}




