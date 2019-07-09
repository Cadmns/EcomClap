package techlab.digital.com.ecommclap.activity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.CityLocationAdapter;
import techlab.digital.com.ecommclap.model.CityLocationModel.CityLocationResponse;
import techlab.digital.com.ecommclap.network.ApiClient;
import techlab.digital.com.ecommclap.network.ApiInterface;
import techlab.digital.com.ecommclap.utility.Const;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class SelectCityActivity extends AppCompatActivity implements View.OnClickListener,CityLocationAdapter.OnInterfaceListener{
    TextView header_message,page_slogan;
    String city_name;
   // CardView city_noida,city_delhi,city_gzb,city_gurgaon;
    boolean noida,delhi,gzb,gurugram = Boolean.FALSE;
    Button confirm_city_btn; Bundle extras;
    String path_flow;
    SessionManager sessionManager;
    CityLocationAdapter mAdapter;
    RecyclerView recyclerView;
    Drawable highlight;
    Drawable highlight_remove;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        sessionManager = new SessionManager(getApplicationContext());


        Intent intent = getIntent();
        if (null != intent.getExtras()) {
            extras = intent.getExtras();
            path_flow= extras.getString("edttext");

        }
        else
        {
            path_flow ="new_signIn";
        }

        fetchCityLocation();
        //init();
        newInit();
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        Typeface custom_fonts = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        recyclerView=findViewById(R.id.cityrecycleView);
        header_message = findViewById(R.id.header_message);
        header_message.setTypeface(custom_font);
        header_message.setText(getResources().getString(R.string.almost_there));
        page_slogan = findViewById(R.id.page_slogan);
        page_slogan.setTypeface(custom_fonts);
        page_slogan.setText(getResources().getString(R.string.recommend_message_for_cities));

    }

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    public void onBackPressed() {
        if (path_flow.equals("FromMainMenu")){

            super.onBackPressed();
        }else {

            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);

            } else {

                Toast.makeText(getBaseContext(), "Press once again to exit!",
                        Toast.LENGTH_SHORT).show();


            }

            back_pressed = System.currentTimeMillis();
            //finish();
            // super.onBackPressed();
        }
        }

private void newInit()
{
   highlight= getResources().getDrawable( R.drawable.highlight);
    highlight_remove = getResources().getDrawable( R.drawable.highlight_remove);
    confirm_city_btn=(Button)findViewById(R.id.letsGo);
    confirm_city_btn.setOnClickListener(this);
}
   /* private void init(){
        final Drawable highlight = getResources().getDrawable( R.drawable.highlight);
        final Drawable highlight_remove = getResources().getDrawable( R.drawable.highlight_remove);
        city_gurgaon  =(CardView)findViewById(R.id.gurugram);
        confirm_city_btn=(Button)findViewById(R.id.letsGo);
        confirm_city_btn.setOnClickListener(this);
        city_noida =(CardView)findViewById(R.id.noida);
        city_delhi =(CardView)findViewById(R.id.delhi);
        city_gzb =(CardView)findViewById(R.id.Gzb);
        city_noida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_city_btn.setVisibility(View.VISIBLE);
                noida = true;
                city_noida.setBackground(highlight);
                city_delhi.setBackground(highlight_remove);
                city_gzb.setBackground(highlight_remove);
                city_gurgaon.setBackground(highlight_remove);

                city_name = "Noida";

            }
        });

        city_delhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_city_btn.setVisibility(View.VISIBLE);
                delhi = true;
                city_delhi.setBackground(highlight);
                city_noida.setBackground(highlight_remove);
                city_gzb.setBackground(highlight_remove);
                city_gurgaon.setBackground(highlight_remove);



                city_name = "Delhi";

            }
        });

        city_gzb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_city_btn.setVisibility(View.VISIBLE);
                gzb = true;
                city_gzb.setBackground(highlight);
                city_noida.setBackground(highlight_remove);
                city_delhi.setBackground(highlight_remove);
                city_gurgaon.setBackground(highlight_remove);

                city_name = "Ghaziabad";
            }
        });


        city_gurgaon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                confirm_city_btn.setVisibility(View.VISIBLE);
                gurugram = true;
                city_gzb.setBackground(highlight_remove);
                city_noida.setBackground(highlight_remove);
                city_delhi.setBackground(highlight_remove);
                city_gurgaon.setBackground(highlight);


                city_name = "Gurugram";


               // Toast.makeText(getApplicationContext(),"Not Available",Toast.LENGTH_SHORT).show();
            }
        });


    }*/

    @Override
    public void onClick(View v) {
        if (v == confirm_city_btn){
            sessionManager.setUserCity(city_name);
            Intent intent = new Intent(getApplicationContext(),NewCategoryActivity.class);
            startActivity(intent);

            }
    }

    ProgressDialog progressDialog;
public  void fetchCityLocation()
{
    progressDialog = new ProgressDialog(SelectCityActivity.this);
    progressDialog.setMessage("Loading..");
    progressDialog.setCancelable(false);
    progressDialog.show();
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<List<CityLocationResponse>> call = apiService.fetchCityLocation();
    call.enqueue(new Callback<List<CityLocationResponse>>() {
        @Override
        public void onResponse(Call<List<CityLocationResponse>> call, Response<List<CityLocationResponse>> response) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            if(response.isSuccessful()){
                // setAdapterViews(response.body());
                if (!response.body().isEmpty()) {
                   Log.e("respose", String.valueOf(response.body()));
                    Log.e("respose", String.valueOf(response.body().get(0).getName()));
                    setAdapter(response.body());
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Not Available",Toast.LENGTH_SHORT).show();
                }
            }else{
                Log.e("Error","");
            }
        }
        @Override
        public void onFailure(Call<List<CityLocationResponse>> call, Throwable t) {
            Log.e("onFailure",t.getMessage());
            if (progressDialog.isShowing())
                progressDialog.dismiss();

        }
    });
}
 public void setAdapter(List<CityLocationResponse> citylocation)
 {
     recyclerView.setHasFixedSize(true);
     int numberOfColumns = 3;
     recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
     mAdapter = new CityLocationAdapter(citylocation,getApplicationContext());
     recyclerView.setAdapter(mAdapter);
     mAdapter.mCallback = (CityLocationAdapter.OnInterfaceListener) this;
 }


    @Override
    public void onCityLocationClick(String cityname,View v,int position) {
        confirm_city_btn.setVisibility(View.VISIBLE);
        city_name=cityname;
         Log.e("city name",cityname);
        Log.e("city%%%name",city_name);
    }
}
