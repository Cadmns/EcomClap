package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.CityLocationModel.CityLocationResponse;


public class CityLocationAdapter extends RecyclerView.Adapter<CityLocationAdapter.MyViewHolder> {

    List<CityLocationResponse> lineItem;
    Context context;
    CityLocationResponse objLineItem;
    public OnInterfaceListener mCallback;
    public  Drawable highlight,highlight_remove;

    public CityLocationAdapter(List<CityLocationResponse> lineItem, Context context) {
        this.lineItem = lineItem;
        this.context = context;
    }

    @NonNull
    @Override
    public CityLocationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.citylocation_list, parent, false);
        return new MyViewHolder(v);
    }
    int row_index=-1;
    @Override
    public void onBindViewHolder(@NonNull final CityLocationAdapter.MyViewHolder holder, final int position) {
        objLineItem=lineItem.get(position);

        holder.cityname.setText(objLineItem.getName());

        holder.cityName_cardv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // cityName_cardv.setBackground(highlight);
                row_index=position;
                notifyDataSetChanged();
                mCallback.onCityLocationClick(lineItem.get(position).getName(),v,position);
            }
        });
        if(row_index==position){
            holder.cityName_cardv.setBackground(highlight);

        }
        else
        {
            holder.cityName_cardv.setBackground(highlight_remove);
        }

    }

    @Override
    public int getItemCount() {
        return lineItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView cityname;
        CardView cityName_cardv;


        public MyViewHolder(View itemView) {
            super(itemView);
            highlight_remove = context.getResources().getDrawable( R.drawable.highlight_remove);
            highlight = context.getResources().getDrawable( R.drawable.highlight);
            cityName_cardv=itemView.findViewById(R.id.cityName_cv);
            cityname = itemView.findViewById(R.id.cityname_tv);

        }
    }

    public interface OnInterfaceListener {

        void onCityLocationClick(String cityname, View v,int position);

    }
}
