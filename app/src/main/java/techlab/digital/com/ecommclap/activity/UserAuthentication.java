package techlab.digital.com.ecommclap.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.utility.AppPreferenceManager;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.Const;

public class UserAuthentication extends AppCompatActivity implements View.OnClickListener {
    AppCompatEditText userNumber;
    AppCompatButton submit;
    ProgressBar bar;
    TextView titleTextView;
    View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initViews();
        setToolBar();

    }

    String TAG = UserAuthentication.class.getSimpleName();
    private void initViews() {

        parentView = findViewById(R.id.relativelayout);
        userNumber = findViewById(R.id.mobile_number);
        submit = findViewById(R.id.btn_signup);
        bar = this.findViewById(R.id.progressBar);
        submit.setEnabled(false);
        submit.setAlpha(0.5f);
        setListeners();
    }

    private void setListeners() {
        submit.setOnClickListener(this);
        userNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isReady = userNumber.getText().toString().length() > 9;
                if (isReady) {
                    submit.setEnabled(true);
                    submit.setAlpha(1.0f);
                    hideKeyboard();
                } else {
                    submit.setEnabled(false);
                    submit.setAlpha(0.5f);
                }
            }
        });

    }

    public void enableSubmitIfReady() {

        boolean isReady = userNumber.getText().toString().length() > 9;
        if (isReady) {
            submit.setEnabled(true);
            submit.setAlpha(1.0f);
            hideKeyboard();

        }

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            assert getSystemService(Context.INPUT_METHOD_SERVICE) != null;
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setToolBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleTextView = tb.findViewById(R.id.toolbarTitle);
        titleTextView.setText(Html.fromHtml(getString(R.string.login_title)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

    // variable to track event time
    private long mLastClickTime = 0;
    @Override
    public void onClick(View v) {
        if (v == submit) {
            if (mCheckInternetWithMultipleClicks()) {
                Intent intent = new Intent(getApplicationContext(), UserConfirmation.class);
                intent.putExtra("phone_number", userNumber.getText().toString());
                startActivity(intent);
            }
        }

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void showSnack(boolean isConnected,String msg) {
        String message;
        int color;
        if (isConnected) {
            message = msg;
            color = Color.WHITE;
        } else {
            message = msg;
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(parentView, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }


}
