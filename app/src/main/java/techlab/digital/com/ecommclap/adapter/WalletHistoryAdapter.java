package techlab.digital.com.ecommclap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.wallet.WalletHistory;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.MyViewHolder> {
    private List<WalletHistory> walletHistoryList;
    Context mtx;
    public WalletHistoryAdapter(Context context,List<WalletHistory> moviesList) {
        this.walletHistoryList = moviesList;
        this.mtx = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView history_title, ammount, time,closing_bal;
        public MyViewHolder(View view) {
            super(view);
            history_title = (TextView) view.findViewById(R.id.history_title);
            ammount = (TextView) view.findViewById(R.id.balance);
            time = (TextView) view.findViewById(R.id.timing);
            //closing_bal = (TextView) view.findViewById(R.id.closing_bal);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WalletHistory walletHistory = walletHistoryList.get(position);
        holder.history_title.setText(walletHistory.getDetails());
        holder.ammount.setText(walletHistory.getAmount());
       // holder.time.setText(walletHistory.getDate());
        String date = walletHistory.getDate();

        holder.time.setText(formateMyDate(date));

//        holder.closing_bal.setText(walletHistory.getBalance());
    }

    @Override
    public int getItemCount() {
        return walletHistoryList.size();
    }

    public String formateMyDate(String mdate){
        Date formatedDate;
        String formateddate1 = "";
        String oldFormat = "yyyy-MM-dd HH:mm:ss";
        String newFormat = "HH:mm:ss dd-MM-yyyy";
        SimpleDateFormat sdfd;
        sdfd = new SimpleDateFormat(oldFormat);
        try {
            formatedDate = sdfd.parse(mdate);

            sdfd.applyPattern(newFormat);
            formateddate1=sdfd.format(formatedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return formateddate1;
    }
}
