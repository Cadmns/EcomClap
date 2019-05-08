package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.AccessControlContext;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.ChatHistoryActivity;
import techlab.digital.com.ecommclap.model.complaints_model.ListComplaint;

public class ComplainListAdapter extends RecyclerView.Adapter<ComplainListAdapter.ViewAdapter>{

    List<ListComplaint> m_complain_list;
    Context mtx;
    ListComplaint objLineItem;
    public ComplainListAdapter(List<ListComplaint> items,Context context) {
        this.m_complain_list = items;
        this.mtx = context;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaints_list, parent, false);
        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, final int position) {
        objLineItem = m_complain_list.get(position);
        /// holder._id.setText(String.valueOf(objLineItem.getId()));
        holder._complain_title.setText(String.valueOf(objLineItem.getTitle().getRendered()));
        holder._date.setText(String.valueOf(objLineItem.getDate()));
        holder._status.setText(String.valueOf(objLineItem.getStatus()));
        holder._state.setText(String.valueOf(objLineItem.getState()));
        holder._type.setText(String.valueOf(objLineItem.getType()));

        holder.complain_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mtx, ChatHistoryActivity.class);
                try {
                    intent.putExtra("complain_Id",m_complain_list.get(position).getId());
                    intent.putExtra("complaint_title", String.valueOf(m_complain_list.get(position).getTitle().getRendered()));
                    intent.putExtra("product_id", String.valueOf(m_complain_list.get(position).getMeta().getOrderId()));
                    intent.putExtra("complaint_date",m_complain_list.get(position).getDate());
                    intent.putExtra("complaint_status",m_complain_list.get(position).getStatus());

                    //intent.putExtra("key_value",44);
                    /*  Log.e("!!@@@!@@@", String.valueOf(m_complain_list.get(position).getId()));*/
                    mtx.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return   m_complain_list.size();
    }



    class ViewAdapter extends RecyclerView.ViewHolder{
        TextView _id,_date,_complain_title,_state,_type;
        Button _status;
        CardView complain_container;
        public ViewAdapter(View itemView) {
            super(itemView);
          //  _id = itemView.findViewById(R.id.complaints_id);
            complain_container = itemView.findViewById(R.id.complain_container);
            _date = itemView.findViewById(R.id.complaints_date);
            _status = itemView.findViewById(R.id.complaints_status);
            _state = itemView.findViewById(R.id.complaints_state);
            _type = itemView.findViewById(R.id.complaints_type);
            _complain_title = itemView.findViewById(R.id.complain_title);

        }


    }
}
