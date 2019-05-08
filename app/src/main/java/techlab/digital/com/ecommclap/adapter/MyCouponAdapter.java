package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.couponModel.CouponDetailsResponse;

public class MyCouponAdapter extends RecyclerView.Adapter<MyCouponAdapter.ViewAdapter> {
    public OnInterfaceListener mCallback;
    String startdate,enddate,month;
    List<CouponDetailsResponse> lineItem;
    Context context;
    CouponDetailsResponse objLineItem;
    SimpleDateFormat sdf;
    String coupon_of;
    // String  couponMonth;

    public MyCouponAdapter(List<CouponDetailsResponse> lineItem, Context context,String coupon_for) {
        this.lineItem = lineItem;
        this.context = context;
        this.coupon_of = coupon_for;
    }

    @NonNull
    @Override
    public MyCouponAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_coupon_list, parent, false);
        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCouponAdapter.ViewAdapter holder, final int position) {
        objLineItem=lineItem.get(position);



        if(coupon_of.equals("all_coupons")){
            holder.apply_btn.setVisibility(View.GONE);
            holder.mcoupon_code.setVisibility(View.VISIBLE);
            holder.mcoupon_code.setText(objLineItem.getCode());
            holder.mcoupon_description.setText(objLineItem.getDescription());
            holder.valid_till.setText(checkedCouponDate(objLineItem.getDateCreated())+" to "+checkedCouponDate(objLineItem.getDateExpires()));

        }
        else {

            holder.mcoupon_code.setVisibility(View.GONE);
            holder.apply_btn.setVisibility(View.VISIBLE);
            holder.mcoupon_code.setText(objLineItem.getCode());
            holder.mcoupon_description.setText(objLineItem.getDescription());

            holder.apply_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onAppliedCoupon(view,position);

                    //  mCallback.onAppliedCoupon(objLineItem.getId(),objLineItem.getCode(),objLineItem.getAmount());
                }
            });
        }


        /*holder.mcoupon_code.setText(objLineItem.getCode());
        holder.mcoupon_description.setText(objLineItem.getDescription());
        holder.valid_till.setText("valid Till "+checkedCouponDate(objLineItem.getDateCreated())+" to "+checkedCouponDate(objLineItem.getDateExpires()));

       *//* holder.apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onAppliedCoupon(view,position);

                //  mCallback.onAppliedCoupon(objLineItem.getId(),objLineItem.getCode(),objLineItem.getAmount());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return   lineItem.size();
    }

    class ViewAdapter extends RecyclerView.ViewHolder  {
        TextView mcoupon_code,mcoupon_description,valid_till;
        TextView apply_btn;
        public ViewAdapter(View itemView) {
            super(itemView);
            apply_btn = itemView.findViewById(R.id.apply_btn);
            mcoupon_code = itemView.findViewById(R.id.coupon_code);
            mcoupon_description = itemView.findViewById(R.id.coupon_description);
            valid_till = itemView.findViewById(R.id.valid_till);

        }


    }



    public interface OnInterfaceListener {
        void onAppliedCoupon(View view,int position);


    }



    String formateddate1;
    Date StartDate,expiredate;
    public String checkedCouponDate(String date) {
        String oldFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String newFormat = "dd-MM-yyyy";
        sdf = new SimpleDateFormat(oldFormat);
        try {
            StartDate = sdf.parse(date);
            sdf.applyPattern(newFormat);
            formateddate1 =sdf.format(StartDate);
            Log.e("format STRINGDATE1= ", String.valueOf(formateddate1));
        } catch (ParseException e) {
            Log.e("There no date Parse",String.valueOf(e));
        }
        return formateddate1;
    }
   /* Date StartDate,expiredate;
    public String checkedCouponDate(String date) {
        String oldFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String newFormat = "dd-MM-yyyy HH:mm:ss";
        sdf = new SimpleDateFormat(oldFormat);
        try {
            StartDate = sdf.parse(date);
            //  expiredate = sdf.parse(enddate);
            sdf.applyPattern(newFormat);
            String formateddate1=sdf.format(StartDate);
            // String formatedDate2 = sdf.format(expiredate);
            Log.e("format STRINGDATE1= ", String.valueOf(formateddate1));
            // Log.e("formateddate 2= ", String.valueOf(expiredate));
        } catch (ParseException e) {
            //e.printStackTrace();
            //Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_successfully_submit), Toast.LENGTH_SHORT).show();
            Log.e("There no date Parse",String.valueOf(e));
        }
        return String.valueOf((StartDate.getDate()));
    }


    public String checkedCouponMonth(String date) {
        String oldFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String newFormat = "dd-MM-yyyy HH:mm:ss";
        sdf = new SimpleDateFormat(oldFormat);
        try {
            StartDate = sdf.parse(date);
            //  expiredate = sdf.parse(enddate);
            sdf.applyPattern(newFormat);
            String formateddate1=sdf.format(StartDate);
            // String formatedDate2 = sdf.format(expiredate);
            Log.e("format STRINGDATE1= ", String.valueOf(formateddate1));
            // Log.e("formateddate 2= ", String.valueOf(expiredate));
        } catch (ParseException e) {
            //e.printStackTrace();
            //Toast.makeText(getApplicationContext(), getResources().getString(R.string.data_successfully_submit), Toast.LENGTH_SHORT).show();
            Log.e("There no date Parse",String.valueOf(e));
        }

        String couponMonth=getMonth(StartDate.getMonth());
        Log.e("month",couponMonth);
        return couponMonth;
    }
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
*/


}
