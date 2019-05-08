package techlab.digital.com.ecommclap.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.fragments.CartFragment;
import techlab.digital.com.ecommclap.fragments.ScheduledProductFragment;

public class ShowScheduledProducts extends AppCompatActivity {
    String parent_id,selecteddate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scheduled_products);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        setToolBar();

        fetchIntentValue();

    }


    private void fetchIntentValue(){

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            parent_id  = extras.getString("parent_id");
            selecteddate = extras.getString("selectedDate");


            loadFragment(ScheduledProductFragment.newInstance("service"));
        }else {
            Toast.makeText(getApplicationContext(),"Unexpected issue",Toast.LENGTH_SHORT).show();
            finish();
        }



    }


    TextView titleTextView;
    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.title_schedule_products));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    private void loadFragment(Fragment selectedFragment) {

        Bundle bundle = new Bundle();
        bundle.putString("selectedDate",selecteddate);
        bundle.putString("parent_id",parent_id);
        selectedFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
    }


}
