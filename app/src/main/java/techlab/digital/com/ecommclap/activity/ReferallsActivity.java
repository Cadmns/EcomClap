package techlab.digital.com.ecommclap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;
import techlab.digital.com.ecommclap.utility.CheckInternet;

public class ReferallsActivity extends AppCompatActivity {
    TextView friendship_slogan;
    CardView whtsapp_btn,view_more;
    String app_link="https://play.google.com/store/apps/details?id=com.techlab.igarage";
    @BindView(R.id.referralCode)
    TextView mReferralCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referalls);
        ButterKnife.bind(this);
        friendship_slogan = findViewById(R.id.friendship_slogan);
        whtsapp_btn = findViewById(R.id.whtsapp_btn);
        view_more = findViewById(R.id.more);
        initViews();
        whtsapp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckInternet.isNetwork(getApplicationContext())) {
                    //  PackageManager pm=getPackageManager();
                    sendWhatsapp(app_link);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckInternet.isNetwork(getApplicationContext())) {
                    shareText(view);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


        private void initViews() {
            UserDetailsResponse user_from_shared_prefs = get_User_From_Shared_Prefs(getApplicationContext());
            try {
                if (user_from_shared_prefs.getMetaData().get(1).getKey().equals("referral_id"))
                    mReferralCode.setText("YOUR CODE : "+user_from_shared_prefs.getMetaData().get(1).getValue());
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    private void sendWhatsapp(String message){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }
    public void shareText(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = app_link;
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = app_link;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }








    public UserDetailsResponse get_User_From_Shared_Prefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("user_info", "");
        UserDetailsResponse user = gson.fromJson(json, UserDetailsResponse.class);
        return user;
    }

}