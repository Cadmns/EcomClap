package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.ComplaintActivity;
import techlab.digital.com.ecommclap.fragments.MyOrderBottomSheet;
import techlab.digital.com.ecommclap.model.historyModel.HistoryResponse;

public class ScheduledAdapter extends RecyclerView.Adapter<ScheduledAdapter.ViewHolder> {

    private ArrayList<HistoryResponse> displayItems;
    Context context;
    private String address;
    public OnInterfaceListener mCallback;

    public ScheduledAdapter(Context mActivity, ArrayList<HistoryResponse> ordersModelArrayList) {
        super();
        this.context = mActivity;
        this.displayItems = ordersModelArrayList;

    }

    @NotNull
    @Override
    public ScheduledAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_histories, parent, false);
        return new ScheduledAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull ScheduledAdapter.ViewHolder holder, final int position) {

        if (!(displayItems.size() >0)){
            holder.messages.setVisibility(View.VISIBLE);
        }
        else {

            try {
                final HistoryResponse ordersModel = displayItems.get(position);
                holder.userName.setText(((ordersModel.getShipping().getFirstName() == null) ? ordersModel.getBilling().getFirstName() : ordersModel.getBilling().getFirstName()) + " " + ((ordersModel.getShipping().getLastName() == null) ? ordersModel.getBilling().getLastName() : ordersModel.getBilling().getLastName()));
                if (ordersModel.getPaymentMethod().equals("processing") || ordersModel.getPaymentMethod().equals("COD")) {
                    holder.paid_status.setText(ordersModel.getStatus());
                    holder.payment_method.setText(ordersModel.getPaymentMethodTitle());
                } else {
                    holder.paid_status.setText(ordersModel.getStatus());
                    holder.payment_method.setText("PAID");
                }
                if (ordersModel.getShipping().getAddress1() != null && ordersModel.getShipping().getAddress2() != null) {
                    holder.shipping_adress.setText(ordersModel.getShipping().getAddress1() + " " + ordersModel.getShipping().getAddress2());
                    address = ordersModel.getShipping().getAddress1() + " " + ordersModel.getShipping().getAddress1();
                } else if (ordersModel.getBilling().getAddress1() != null && ordersModel.getBilling().getAddress2() != null) {
                    holder.shipping_adress.setText(ordersModel.getBilling().getAddress1() + " " + ordersModel.getBilling().getAddress2());
                    address = ordersModel.getBilling().getAddress1() + " " + ordersModel.getBilling().getAddress2();
                } else {
                    holder.shipping_adress.setText("Not Provided");
                    address = "";

                }
                holder.phone_number.setText((ordersModel.getBilling().getPhone() == null || ordersModel.getBilling().getPhone().equals("")) ? "N/A" : ordersModel.getBilling().getPhone());
                holder.total_amount.setText((ordersModel.getTotal() == null) ? "N/A" : ordersModel.getTotal());
                holder.order_id.setText((ordersModel.getNumber() == null) ? "N/A" : ordersModel.getNumber());
                holder.delivery_slot.setText((ordersModel.getDateCreated() == null) ? "N/A" : ordersModel.getDateCreated());
                holder.viewbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      mCallback.onItemsClick(view,position);

                    }
                });
                holder.complain_raise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ComplaintActivity.class);
                        intent.putExtra("order_id",ordersModel.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });



            }
            catch (NullPointerException e){
                e.printStackTrace();
            }


        }
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        TextView userName, paid_status, payment_method,shipping_adress,total_amount,phone_number,messages,delivery_slot,order_id; /*View cardView;*/
        Button viewbutton,complain_raise;

        public ViewHolder(View itemView) {
            super(itemView);
            complain_raise=itemView.findViewById(R.id.complain_raised);
            viewbutton=itemView.findViewById(R.id.view_more);
            delivery_slot = itemView.findViewById(R.id.slot);
            order_id = itemView.findViewById(R.id.order_id);
            userName = itemView.findViewById(R.id.orderTitle);
            paid_status = itemView.findViewById(R.id.paid_status);
            payment_method = itemView.findViewById(R.id.paymentMethod);
            shipping_adress = itemView.findViewById(R.id.shipping_address);
            total_amount = itemView.findViewById(R.id.number);
            phone_number= itemView.findViewById(R.id.phone_number);
            messages = itemView.findViewById(R.id.messages);


        }

    }

    public interface OnInterfaceListener {
        void onItemsClick(View view, int position);
    }

}