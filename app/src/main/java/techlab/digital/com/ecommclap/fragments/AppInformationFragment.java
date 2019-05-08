package techlab.digital.com.ecommclap.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.AboutUsActivity;
import techlab.digital.com.ecommclap.activity.ContactUsActivity;
import techlab.digital.com.ecommclap.utility.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppInformationFragment extends Fragment {
    View view;
    private long mLastClickTime = 0;
    SessionManager sessionManager;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    public AppInformationFragment() {
        // Required empty public constructor
    }

    public static AppInformationFragment newInstance() {
        return new AppInformationFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_app_information, container, false);
        ButterKnife.bind(this,view);
      //  getChildFragmentManager().beginTransaction().replace(R.id.containerView, new MainPreferenceFragment()).commit();

       // getFragmentManager().beginTransaction().replace(R.id.containerView, new MainPreferenceFragment()).commit();

        return view;
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
            addPreferencesFromResource(R.xml.pref_app_services);

            initPreferences();
        }

        private void initPreferences(){
            PreferenceScreen screen = getPreferenceScreen();
            final SessionManager sessionManager = new SessionManager(getActivity());


            Preference about_us  = findPreference(getString(R.string.key_about_us));
            Preference contact_us  = findPreference(getString(R.string.key_contact));


            contact_us.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //  sendFeedback(getActivity());

                    Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                    startActivity(intent);

                    return true;
                }
            });

            about_us.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //  sendFeedback(getActivity());

                    Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                    startActivity(intent);

                    return true;
                }
            });

        }
    }

}
