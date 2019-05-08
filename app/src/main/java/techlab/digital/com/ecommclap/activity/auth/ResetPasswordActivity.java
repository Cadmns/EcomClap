package techlab.digital.com.ecommclap.activity.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ResetPasswordActivity extends AppCompatActivity {
    @BindView(R.id.change_password_btn)
    Button submit_btn;

    @BindView(R.id.input_first_password)
    EditText first_password;

    @BindView(R.id.input_second_password)
    EditText second_password;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        sessionManager = new SessionManager(getApplicationContext());

        ButterKnife.bind(this);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("!!!fdhfkdjfkjdh!!!#cpp", String.valueOf(sessionManager.getKeySession()));

                if(first_password.equals(second_password)&&!first_password.equals("")&&!second_password.equals(""))
                    update_password();
                else
                    Toast.makeText(getApplicationContext(),"Enter the All details",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void update_password(){

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Object> call = apiService.update_user_password("Bearer " + sessionManager.getKeySession(),sessionManager.getUserId(), String.valueOf(first_password));

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if(response.isSuccessful()){
                        Log.e("!!!!!!#cpp", String.valueOf(response.body()));
                    }else{
                        Log.e("Error", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.e("onFailure",t.getMessage());

                }
            });


    }



}
