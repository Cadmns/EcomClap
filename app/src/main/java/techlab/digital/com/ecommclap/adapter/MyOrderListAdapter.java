package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.historyModel.LineItem;

public class MyOrderListAdapter  extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder> {

    List<LineItem> lineItem;
    Context context;
    LineItem objLineItem;
    public MyOrderListAdapter(Context contx,List<LineItem> mFinalList) {
        this.lineItem=mFinalList;
        this.context=contx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        objLineItem=lineItem.get(position);
        holder.product_name.setText(objLineItem.getName());
        holder.item_quantity.setText(""+objLineItem.getQuantity());
        holder.grand_total.setText(objLineItem.getTotal());
    }

    @Override
    public int getItemCount() {
        return lineItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        TextView product_name,item_quantity, grand_total;

        public ViewHolder(View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.Product_Name);
            item_quantity = itemView.findViewById(R.id.item_quantity);
            grand_total = itemView.findViewById(R.id.grandTotal);
        }

    }
}
