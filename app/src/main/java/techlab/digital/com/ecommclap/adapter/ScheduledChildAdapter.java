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

import java.util.ArrayList;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.historyModel.HistoryResponse;
import techlab.digital.com.ecommclap.model.historyModel.LineItem;

public class ScheduledChildAdapter  extends RecyclerView.Adapter<ScheduledChildAdapter.MyViewHolder> {

    private Context mContext;
    public OnInterfaceListener mCallback;
    List<LineItem> mLineItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        View top,bottom;
        public TextView mTitle,mPrice,delvieryCharges,mRemove;
        EditText mNoofItems;
        ElegantNumberButton quantity;
        LinearLayout mDeleteProduct,RemoveContainer;
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
            top = (View) view.findViewById(R.id.viewtop);
            bottom = (View) view.findViewById(R.id.viewBottom);
            frameLayout.setVisibility(View.GONE);
            mDeleteProduct.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
            quantity.setRange(1,100);

        }






    }


    public ScheduledChildAdapter(Context context, List<LineItem> mProducts) {
        mContext = context;
        this.mLineItems = mProducts;
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
        LineItem fetchCartResponse = mLineItems.get(position);
        try{

            holder.mTitle.setText(fetchCartResponse.getName());
            holder.quantity.setNumber(String.valueOf(fetchCartResponse.getQuantity()));
            holder.mProductPrice.setText("Rs : "+String.valueOf(fetchCartResponse.getTotal()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }



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
        return mLineItems.size();
    }

    public interface OnInterfaceListener {

        void onUpdateItemsClick(View view, int position, int quantity);

    }
}