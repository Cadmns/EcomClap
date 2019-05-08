package techlab.digital.com.ecommclap.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.ChatHistoryAdapter;
import techlab.digital.com.ecommclap.fragments.ViewComplainDetailsSheet;
import techlab.digital.com.ecommclap.model.complaints_model.add_complain_replies.AddReplyReq;
import techlab.digital.com.ecommclap.model.complaints_model.add_complain_replies.ReplyComplaintsResponse;
import techlab.digital.com.ecommclap.model.complaints_model.get_complain_replies.Collection;
import techlab.digital.com.ecommclap.model.complaints_model.get_complain_replies.GetChatHistory;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ChatHistoryActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TextView no_chats,complain_details_btn,titleTextView;
    EditText message_text;
    Button send_reply;
    private long mLastClickTime = 0;
    ChatHistoryAdapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    String order_id,order_date,complaint_title,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);
        setToolBar();
        complain_details_btn = findViewById(R.id.view_complain_details);
        message_text = findViewById(R.id.lblMsgFrom);
        mRecyclerView = findViewById(R.id.message_recycler_view);
        no_chats = findViewById(R.id.no_chats);
        send_reply = findViewById(R.id.send_reply_btn);
        Intent intent = getIntent();
        String ticket_id = "";
        if (null != intent.getExtras()) {
            ticket_id= String.valueOf(getIntent().getExtras().getInt("complain_Id"));
            complaint_title = String.valueOf(getIntent().getExtras().getString("complaint_title"));
            order_id = String.valueOf(getIntent().getExtras().getString("product_id"));
            order_date =  String.valueOf(getIntent().getExtras().getString("complaint_date"));
            status =  String.valueOf(getIntent().getExtras().getString("complaint_status"));


            /*set something to textview if order id not provided from server side*/


        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }
        sessionManager = new SessionManager(getApplicationContext());
       /* fetch_chats_from_server();*/
        fetch_chats_from_server(ticket_id);
        final String finalTicket_id = ticket_id;
        send_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckInternetWithMultipleClicks()) {

                    if (message_text.getText().toString().matches("")) {

                        Toast.makeText(getApplicationContext(), "Please enter the message", Toast.LENGTH_SHORT).show();
                    } else {
                        add_reply(finalTicket_id);
                    }
                }
            }
        });
        complain_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewComplainDetailsSheet fragment = new ViewComplainDetailsSheet(getApplicationContext(),complaint_title,order_id,order_date,status);
                fragment.show(getSupportFragmentManager(), "TAG");
            }
        });

    }


    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText("Your Chats");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    ProgressDialog progressDialog;
    private void fetch_chats_from_server(String mticket_id){
        progressDialog = new ProgressDialog(this);
        // Setting up message in Progress dialog.
        progressDialog.setMessage("loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<GetChatHistory>> call = apiService.get_chat_history(mticket_id,"Bearer " + sessionManager.getKeySession());
        call.enqueue(new Callback<List<GetChatHistory>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<List<GetChatHistory>> call, @NonNull Response<List<GetChatHistory>> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if(response.isSuccessful()) {

                    if(response.body().isEmpty()){
                        no_chats.setVisibility(View.VISIBLE);
                    }else{
                        setAdapter(response.body());
                        no_chats.setVisibility(View.GONE);
                        Log.e("cp Object to Json android-----", String.valueOf(response.body().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetChatHistory>>  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }

    private void setAdapter(List<GetChatHistory> mFinalList) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatHistoryAdapter(mFinalList,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
 }
     private void add_reply(final String mticket_id){

        String message_slug = String.valueOf(message_text.getText());
        Log.e("ohii cp", String.valueOf(mticket_id));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Object> call = apiService.add_Reply("Bearer " + sessionManager.getKeySession(),mticket_id,message_slug, String.valueOf(sessionManager.getUserId()));
        call.enqueue(new Callback<Object>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {

                if(response.isSuccessful()) {
                        Log.e("cp Object to Json android-----", String.valueOf(response.body().toString()));
                        message_text.setText("");
                        fetch_chats_from_server(mticket_id);
                }
            }

            @Override
            public void onFailure(Call<Object>  call, Throwable t) {
                Log.e("onFailure",t.getLocalizedMessage());
            }
        });
    }


    private Boolean mCheckInternetWithMultipleClicks(){

        if (CheckInternet.isNetwork(getApplicationContext())) {
            /* if (sessionManager.isLoggedIn()) {*/
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;
            //  }
        }else {
            //do something, net is not connected
            Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

}
