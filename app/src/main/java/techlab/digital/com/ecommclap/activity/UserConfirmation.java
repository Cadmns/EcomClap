package techlab.digital.com.ecommclap.activity;


import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.phoneNumberConfirmation.NumberConfrimResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.AppPreferenceManager;
import techlab.digital.com.ecommclap.utility.CheckInternet;
import techlab.digital.com.ecommclap.utility.Const;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class UserConfirmation extends AppCompatActivity implements View.OnClickListener,TextWatcher, View.OnFocusChangeListener, View.OnKeyListener {
    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinFifthDigitEditText;
    private EditText mPinSixthDigitEditText;
    private EditText mPinHiddenEditText;
    SessionManager sessionManager;
    TextView resend_code;
    String user_phone_number;
    Button submit_btn; TextView header_message;
    String TAG = UserConfirmation.class.getSimpleName();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    // [START declare_auth]
    private FirebaseAuth mAuth;View parentView;
    private boolean mVerificationInProgress = false;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    ProgressBar progressBar;
    ProgressDialog loading;

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            assert getSystemService(Context.INPUT_METHOD_SERVICE) != null;
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setListneres(){
        mPinHiddenEditText.addTextChangedListener(this);

        mPinFirstDigitEditText.setOnFocusChangeListener(this);
        mPinSecondDigitEditText.setOnFocusChangeListener(this);
        mPinThirdDigitEditText.setOnFocusChangeListener(this);
        mPinForthDigitEditText.setOnFocusChangeListener(this);
        mPinFifthDigitEditText.setOnFocusChangeListener(this);
        mPinSixthDigitEditText.setOnFocusChangeListener(this);

        mPinFirstDigitEditText.setOnKeyListener(this);
        mPinSecondDigitEditText.setOnKeyListener(this);
        mPinThirdDigitEditText.setOnKeyListener(this);
        mPinForthDigitEditText.setOnKeyListener(this);
        mPinFifthDigitEditText.setOnKeyListener(this);
        mPinSixthDigitEditText.setOnKeyListener(this);
        mPinHiddenEditText.setOnKeyListener(this);




        resend_code.setOnClickListener(this);
        submit_btn.setOnClickListener(this);

    }

    private void initFirebase(){

        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                if ( loading.isShowing())
                    loading.dismiss();
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if ( loading.isShowing())
                    loading.dismiss();
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request

                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.wrong_phone_number),
                            Snackbar.LENGTH_SHORT).show();
                    //  finish();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded

                    /*add dialog box */
                    /* Toast.makeText(getApplicationContext(),getResources().getString(R.string.too_many_otp_request),Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.too_many_otp_request),
                            Snackbar.LENGTH_SHORT).show();
                }

                // Show a message and update the UI
                updateUI(STATE_VERIFY_FAILED);

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                if (loading.isShowing())
                    loading.dismiss();
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                int color = Color.WHITE;
                Snackbar snackbar = Snackbar
                        .make(parentView, getResources().getString(R.string.code_sent), Snackbar.LENGTH_LONG);

                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(color);
                if(!snackbar.isShownOrQueued())
                    snackbar.show();

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                updateUI(STATE_CODE_SENT);

            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        loading.show();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loading.dismiss();


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                mPinHiddenEditText.setText("");
                                mPinFirstDigitEditText.setText("");
                                mPinSecondDigitEditText.setText("");
                                mPinThirdDigitEditText.setText("");
                                mPinForthDigitEditText.setText("");
                                mPinFifthDigitEditText.setText("");
                                mPinSixthDigitEditText.setText("");

                     /*           if (mPinHiddenEditText.length() > 0)
                                    mPinHiddenEditText.setText(mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1));*/

                                Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.invalid_code),
                                        Snackbar.LENGTH_SHORT).show();


                                loading.dismiss();


                                //   user_otp.setText("");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
        // mStatusText.setVisibility(View.INVISIBLE);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        // [START_EXCLUDE]
        if (mVerificationInProgress) {
            startPhoneNumberVerification(user_phone_number);
        }
        // [END_EXCLUDE]
    }

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
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


        if (v == resend_code) {
            if (mCheckInternetWithMultipleClicks()) {
                loading.show();
                resendVerificationCode(user_phone_number, mResendToken);
            }
        }
        if (v == submit_btn){

            if (mCheckInternetWithMultipleClicks()) {

                String code = mPinFirstDigitEditText.getText().toString() + mPinSecondDigitEditText.getText().toString() + mPinThirdDigitEditText.getText().toString() + mPinForthDigitEditText.getText().toString() + mPinFifthDigitEditText.getText().toString() + mPinSixthDigitEditText.getText().toString();
                //  String password = user_password.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.provide_credentials), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(code)) {

                    loading.show();
                    verifyPhoneNumberWithCode(mVerificationId, code);
                }
                if (!TextUtils.isEmpty(code)) {

                    loading.show();
                    verifyPhoneNumberWithCode(mVerificationId, code);

                }
                if (TextUtils.isEmpty(code)) {

                    //loading.show();
                    //    checkUserCredentials(user_phone_number);

                }
            }

        }
    }


    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:

                break;
            case STATE_CODE_SENT:

                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                //     enableViews(submit_btn, resend_code, user_otp);

                loading.dismiss();
                break;
            case STATE_VERIFY_SUCCESS:
                loading.dismiss();

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        //  user_otp.setText(cred.getSmsCode());

                        setOtpCodeToEditText(cred.getSmsCode());
                    } else {
                        //  user_otp.setHint(R.string.instant_validation);
                        //   user_otp.setTextColor(Color.parseColor("#4bacb8"));
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check

                loading.dismiss();
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check

                break;
        }

        if (user == null) {
            /*user sign out message*/
        } else {
            sessionManager.setUserNumber(user_phone_number);
            sessionManager.setNumberVerifiedSatusFlag(true);
            if (mCheckInternetWithMultipleClicks()){

              /*  Intent intent = new Intent(getApplicationContext(),NewCategoryActivity.class);
                startActivity(intent);*/




                Intent intent = new Intent(getApplicationContext(), SelectCityActivity.class);
                Bundle bundles = new Bundle();
                bundles.putString("edttext", "FromStarting");
                intent.putExtras(bundles);
                startActivity(intent);
            }


        }
    }


    private void setOtpCodeToEditText(String str){
        String character1 = String.valueOf(str.charAt(0));
        String character2 = String.valueOf(str.charAt(1));
        String character3 = String.valueOf(str.charAt(2));
        String character4 = String.valueOf(str.charAt(3));
        String character5 = String.valueOf(str.charAt(4));
        String character6 = String.valueOf(str.charAt(5));
        mPinFirstDigitEditText.setText(character1);
        mPinSecondDigitEditText.setText(character2);
        mPinThirdDigitEditText.setText(character3);
        mPinForthDigitEditText.setText(character4);
        mPinFifthDigitEditText.setText(character5);
        mPinSixthDigitEditText.setText(character6);

    }

    AppPreferenceManager appPreferenceManager;

    @Override
    public void onBackPressed() {
        mAuth.signOut();
        super.onBackPressed();
    }



    /*Below is the Testing Code, So if any problem is create than remove the below code
    Testing Code*/



    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Initialize EditText fields.
     */
    private void inits() {
        mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
        mPinFifthDigitEditText = (EditText) findViewById(R.id.pin_fifth_edittext);
        mPinHiddenEditText = (EditText) findViewById(R.id.pin_hidden_edittext);
        mPinSixthDigitEditText =(EditText) findViewById(R.id.pin_sixth_edittext);

        mPinFirstDigitEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        mPinSecondDigitEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        mPinThirdDigitEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        mPinForthDigitEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        mPinFifthDigitEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        mPinHiddenEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        mPinSixthDigitEditText.setRawInputType(Configuration.KEYBOARD_12KEY);


        resend_code= findViewById(R.id.resend_code);
        submit_btn= findViewById(R.id.btn_submit);
        header_message= findViewById(R.id.header_message);
        loading = new ProgressDialog(this,  R.style.MyDialogTheme);
        loading.setMessage("Please wait...");
        loading.setCancelable(false);


        // customTextView(header_message);
        setListneres();


        //  setOtpCode();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MainLayout(this, null));

        parentView = findViewById(R.id.parentView);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        appPreferenceManager = new AppPreferenceManager(getApplicationContext());
        sessionManager= new SessionManager(getApplicationContext());
        initFirebase();
        mAuth = FirebaseAuth.getInstance();
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        progressBar = findViewById(R.id.progressBar);
        inits();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            user_phone_number=intent.getStringExtra("phone_number");
            String temp = getResources().getString(R.string.otp_message);
            temp = temp + " "+ user_phone_number + " "+"\n"+getResources().getString(R.string.fall_to_detct);

            header_message.setText(temp);
            startPhoneNumberVerification(user_phone_number);
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        switch (id) {
            case R.id.pin_first_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_second_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_third_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_forth_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_fifth_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;
            case R.id.pin_sixth_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            switch (id) {
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (mPinHiddenEditText.getText().length() == 6)
                            mPinSixthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 5)
                            mPinFifthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 4)
                            mPinForthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 3)
                            mPinThirdDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 2)
                            mPinSecondDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 1)
                            mPinFirstDigitEditText.setText("");

                        if (mPinHiddenEditText.length() > 0) {
                            mPinHiddenEditText.setText(mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1));
                            mPinHiddenEditText.post(new Runnable() {
                                @Override
                                public void run() {
                                    mPinHiddenEditText.setSelection(mPinHiddenEditText.getText().toString().length());
                                }
                            });
                        }
                        return true;
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (s.length() == 0) {
            //   setFocusedPinBackground(mPinFirstDigitEditText);
            mPinFirstDigitEditText.setText("");
        } else if (s.length() == 1) {
            //    setFocusedPinBackground(mPinSecondDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText("");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");

        } else if (s.length() == 2) {
            //      setFocusedPinBackground(mPinThirdDigitEditText);
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 3) {
            //    setFocusedPinBackground(mPinForthDigitEditText);
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 4) {
            //     setFocusedPinBackground(mPinFifthDigitEditText);
            mPinForthDigitEditText.setText(s.charAt(3) + "");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 5) {
            //    setDefaultPinBackground(mPinFifthDigitEditText);
            mPinFifthDigitEditText.setText(s.charAt(4) + "");

            mPinSixthDigitEditText.setText("");
            //   hideSoftKeyboard(mPinFifthDigitEditText);
        }
        else if (s.length() == 6){
            mPinSixthDigitEditText.setText(s.charAt(5) + "");
            //  mPinSixthDigitEditText.setText("");

            hideSoftKeyboard(mPinSixthDigitEditText);
        }
    }

    /**
     * Sets default PIN background.
     *
     * @param editText edit text to change
     */
    private void setDefaultPinBackground(EditText editText) {
        //  setViewBackground(editText, getResources().getDrawable(android.R.drawable.tra));
    }

    /**
     * Sets focus on a specific EditText field.
     *
     * @param editText EditText to set focus on
     */
    public static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    /**
     * Sets focused PIN field background.
     *
     * @param editText edit text to change
     */
    private void setFocusedPinBackground(EditText editText) {
        // setViewBackground(editText, getResources().getDrawable(R.drawable.textfield_focused_holo_light));
    }


    /**
     * Sets background of the view.
     * This method varies in implementation depending on Android SDK version.
     *
     * @param view       View to which set background
     * @param background Background to set to view
     */
    @SuppressWarnings("deprecation")
    public void setViewBackground(View view, Drawable background) {
        if (view == null || background == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    /**
     * Custom LinearLayout with overridden onMeasure() method
     * for handling software keyboard show and hide events.
     */
    public class MainLayout extends LinearLayout {

        public MainLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.user_confirmqation_test, this);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            final int proposedHeight = View.MeasureSpec.getSize(heightMeasureSpec);
            final int actualHeight = getHeight();

            Log.d("TAG", "proposed: " + proposedHeight + ", actual: " + actualHeight);

            if (actualHeight >= proposedHeight) {
                // Keyboard is shown
                if (mPinHiddenEditText.length() == 0)
                    setFocusedPinBackground(mPinFirstDigitEditText);
                else
                    setDefaultPinBackground(mPinFirstDigitEditText);
            }

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}

