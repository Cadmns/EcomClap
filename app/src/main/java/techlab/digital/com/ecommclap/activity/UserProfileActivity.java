package techlab.digital.com.ecommclap.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.auth.LoginActivity;
import techlab.digital.com.ecommclap.activity.schedule_products.SchedulableItemsActivity;
import techlab.digital.com.ecommclap.activity.wallet_activity.EcomWalletActivity;
import techlab.digital.com.ecommclap.activity.wallet_activity.ShowUserOrdersActivity;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class UserProfileActivity extends AppCompatActivity {
    TextView titleTextView;
    @BindView(R.id.customerName)
    TextView mName;
    @BindView(R.id.email)
    TextView mEmail;
    SessionManager sessionManager;

    @BindView(R.id.profile_container)
    RelativeLayout mprofile_container;
    @BindView(R.id.not_login_yet)
    LinearLayout mnot_login_yet;
    @BindView(R.id.login_first_btn)
    TextView login_here_btn;
    // variable to track event time`
    private long mLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
        setToolBar();
        initViews();
        if(sessionManager.isLoggedIn())
        {
            mprofile_container.setVisibility(View.VISIBLE);
        }
        else{
            mprofile_container.setVisibility(View.GONE);
            mnot_login_yet.setVisibility(View.VISIBLE);
        }

        getFragmentManager().beginTransaction().replace(R.id.containerView, new MainPreferenceFragment()).commit();
        login_here_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckInternetWithMultipleClicks())
                    startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
            }
        });
    }

    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(getResources().getString(R.string.title_user_profile));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initViews(){

        for (Map.Entry<String,String> entry : sessionManager.getUserDetails().entrySet()){
        if (entry.getKey().equals("name"))
        mName.setText((entry.getValue()== null)? "Hi folks!" :entry.getValue());
        else if (entry.getKey().equals("email")){
            mEmail.setText(entry.getValue());
        }
        }


    }


    public static class MainPreferenceFragment extends PreferenceFragment {
        String version;  protected FragmentActivity mActivity;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            view.setBackground(getResources().getDrawable(R.drawable.background));

            return view;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            mActivity = (FragmentActivity) activity;
        }
        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            View rootView = getView();
            ListView list = (ListView) rootView.findViewById(android.R.id.list);
            ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.disale_color));
            list.setDivider(sage);
        }
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            initPreferences();
        }


        private void initPreferences(){
            PreferenceScreen screen = getPreferenceScreen();
            final SessionManager sessionManager = new SessionManager(getActivity());

            Preference my_account = findPreference(getString(R.string.key_my_account));
            Preference schedule_myproduct = findPreference(getString(R.string.key_schedule_my_product));
            Preference my_orders = findPreference(getString(R.string.key_my_orders));

            Preference my_wallet  = findPreference(getString(R.string.key_my_wallet));
          //  Preference my_coupons  = findPreference(getString(R.string.key_my_coupons));

            Preference my_scheduled_product  = findPreference(getString(R.string.key_my_scheduled_product));
           // Preference refrence_center  = findPreference(getString(R.string.key_my_reference_center));
            Preference Complaints_query  = findPreference(getString(R.string.key_my_complaints_query));
            Preference log_out =  findPreference(getString(R.string.key_log_out));
            Preference rate_us = findPreference(getString(R.string.key_rate_us));
            Preference my_referral =  findPreference(getString(R.string.key_my_referral));
            Preference log_in =  findPreference(getString(R.string.key_log_in));

            if(sessionManager.isLoggedIn())
            {
                screen.removePreference(log_in);
            }else {
                screen.removePreference(my_account);
                screen.removePreference(my_orders);
                screen.removePreference(my_wallet);
               // screen.removePreference(my_coupons);
                screen.removePreference(my_scheduled_product);
               // screen.removePreference(refrence_center);
                screen.removePreference(Complaints_query);
                screen.removePreference(log_out);
                screen.removePreference(my_referral);

            }
            my_wallet.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    if(CheckInternet.isNetwork(getActivity())) {
                        Intent intent = new Intent(getActivity(), EcomWalletActivity.class);
                        intent.putExtra("flow", "profile");
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
                    }


                    return true;
                }
            });

            my_account.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    if(CheckInternet.isNetwork(getActivity())) {
                        Intent intent = new Intent(getActivity(), MyAccountActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });
            my_orders.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {

                    if(CheckInternet.isNetwork(getActivity())){
                        Intent intent = new Intent(getActivity(), ShowUserOrdersActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();

                    }
                      return true;
                }
            });

            log_out.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    if (CheckInternet.isNetwork(getActivity())) {
                        sessionManager.logoutUser();
                        Intent intent = new Intent(getActivity(), UserAuthentication.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "logout succesfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();

                    }
                    return true;
                }
            });

            my_referral.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), ReferallsActivity.class);
                    startActivity(intent);

                    return true;
                }
            });




            Complaints_query.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    if(CheckInternet.isNetwork(getActivity())){
                        Intent intent = new Intent(getActivity(),ComplaintActivity.class);
                        intent.putExtra("order_id",-1);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            log_in.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //  sendFeedback(getActivity());

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    return true;
                }
            });

            my_scheduled_product.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), ScheduledParentsProductsActivity.class);
                    startActivity(intent);

                    return true;
                }
            });


            my_scheduled_product.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), ScheduledParentsProductsActivity.class);
                    startActivity(intent);

                    return true;
                }
            });


            rate_us.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //  sendFeedback(getActivity());

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.techlab.igarage")));

                    return true;
                }
            });




            schedule_myproduct.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //  sendFeedback(getActivity());

                    Intent intent = new Intent(getActivity(), SchedulableItemsActivity.class);
                    startActivity(intent);

                    return true;
                }
            });

        }

    }


    private Boolean mCheckInternetWithMultipleClicks(){

        if (CheckInternet.isNetwork(getApplicationContext())) {
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
