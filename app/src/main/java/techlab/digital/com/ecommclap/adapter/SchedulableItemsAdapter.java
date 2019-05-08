package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.schedule_products.ItemsSchedulerActivity;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponsetTwo;

public class SchedulableItemsAdapter  extends RecyclerView.Adapter<SchedulableItemsAdapter.ViewHolder> {

    private List<ProductListingsModeResponsetTwo> displayItems;
    Context context;
    private String address;
    public OnInterfacListener mcallBack;
    public SchedulableItemsAdapter(Context mActivity, List<ProductListingsModeResponsetTwo> ordersModelArrayList) {
        super();
        this.context = mActivity;
        this.displayItems = ordersModelArrayList;

    }

    @Override
    public SchedulableItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedulable_product_list, parent, false);
        return new SchedulableItemsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SchedulableItemsAdapter.ViewHolder holder, final int position) {
        final ProductListingsModeResponsetTwo mResponse = displayItems.get(position);
        holder.product_name.setText((mResponse.getName()== null ||mResponse.getName().equals("")) ? "N/A" : mResponse.getName());
        holder.mproduct_dsc.setText(removeHtml(String.valueOf(Html.fromHtml(mResponse.getDescription()))));
        try
        {
            Glide.with(context)
                    .load(mResponse.getImages().get(0).getSrc()) // Remote URL of image.
                    .into(holder.image_cartlist);
        }
        catch(Exception e)
        {
            Log.e("error glide", String.valueOf(e));
        }
        holder.mQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton vw, int oldValue, int newValue) {
                if (newValue !=0)
                    mcallBack.onselectItem(position,newValue,mResponse.getId());
                else
                    Toast.makeText(context,"Quantity must be minimum 1",Toast.LENGTH_SHORT).show();

            }
        });
       /* holder.mcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemsSchedulerActivity.class);
                intent.putExtra("response",mResponse);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        TextView product_name,mproduct_dsc;
        LinearLayout mcardview;
        ElegantNumberButton mQuantity;
        ImageView image_cartlist;
        public ViewHolder(View itemView) {
            super(itemView);
            mcardview = itemView.findViewById(R.id.schedule_item_layout);
            mproduct_dsc = itemView.findViewById(R.id.mproduct_dsc);
            product_name = itemView.findViewById(R.id.mproduct_name);
            mQuantity= itemView.findViewById(R.id.number_quantity);
            image_cartlist=itemView.findViewById(R.id.image_cart);
        }

    }
    private String removeHtml(String html) {
        String noHTMLString = html.replaceAll("\\<.*?\\>", "");
        // Remove Carriage return from java String
        noHTMLString = noHTMLString.replaceAll("\r", "<br/>");
        noHTMLString = noHTMLString.replaceAll("<([bip])>.*?</\1>", "");
        // Remove New line from java string and replace html break
        noHTMLString = noHTMLString.replaceAll("\n", " ");
        noHTMLString = noHTMLString.replaceAll("\"", "&quot;");
        noHTMLString = noHTMLString.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
        noHTMLString = noHTMLString.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
        noHTMLString = noHTMLString.replaceFirst("(.*?)\\>", " ");
        noHTMLString = noHTMLString.replaceAll("&nbsp;"," ");
        noHTMLString = noHTMLString.replaceAll("&amp;"," ");
        return noHTMLString;

    }


    public interface OnInterfacListener {
        void onselectItem(int position,int quantity,int id);

    }

}
