package techlab.digital.com.ecommclap.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import techlab.digital.com.ecommclap.R;

public class ScheduleProductCalenderView extends AppCompatActivity {
    @BindView(R.id.calendar_view)
    CalendarPickerView calendar_view;
    @BindView(R.id.btn_show_dates)
    Button btn_show_dates;
    Date newDate1;
    Date newDate2;
    SimpleDateFormat sdf;
    String oldFormat = "yyyy-MM-dd";
    String newFormat = "EEE MMM dd HH:mm:ss zzz yyyy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_product_calenderview);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        fetchIntentValue();


    }

    public void change_date_formate(String startScheduledate, String endScheduledate){
    sdf = new SimpleDateFormat(oldFormat);
    try {

        newDate1 = sdf.parse(startScheduledate);
        newDate2 = sdf.parse(endScheduledate);
        sdf.applyPattern(newFormat);
        String formateddate1=sdf.format(newDate1);
      /* calendar_view.selectDate(newDate1);
        calendar_view.selectDate(newDate2);*/


        initCalenderView(newDate1,newDate2);

    } catch (ParseException e) {
        e.printStackTrace();
    }
    }

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }


    String myFormat = "yyyy-MM-dd";
    SimpleDateFormat sdf3 = new SimpleDateFormat(myFormat, Locale.US);
    private void initCalenderView(final Date newDate1, Date newDate2){

        //getting current
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();

        //add one year to calendar from todays date
        calendar_view.init(newDate1, newDate2)
                .inMode(CalendarPickerView.SelectionMode.SINGLE);


        //action while clicking on a date
        calendar_view.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

             //   Toast.makeText(getApplicationContext(),"UnSelected Date is : " +date.toString(),Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();

                Intent intent = new Intent(getApplicationContext(),ShowScheduledProducts.class);
                System.out.println(date);

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                String formatedDate = cal.get(Calendar.YEAR) + "-" + checkDigit(cal.get(Calendar.MONTH) + 1) + "-" +checkDigit(cal.get(Calendar.DATE));
                bundle.putString("selectedDate",formatedDate);
                bundle.putString("parent_id",parent_id);

                 intent.putExtras(bundle);

                startActivity(intent);

            }

            @Override
            public void onDateUnselected(Date date) {
                Log.e("TAG",date.toString());
              //  Toast.makeText(getApplicationContext(),"UnSelected Date is : " +date.toString(),Toast.LENGTH_SHORT).show();
            }
        });



//Displaying all selected dates while clicking on a button
       // Button btn_show_dates = (Button) findViewById(R.id.btn_show_dates);
        btn_show_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i< calendar_view.getSelectedDates().size();i++){

                    //here you can fetch all dates
                   // Toast.makeText(getApplicationContext(),calendar_view.getSelectedDates().get(i).toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    String startScheduledate,endScheduledate,orderScheduleType,parent_id;
    private void fetchIntentValue(){

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            startScheduledate  = extras.getString("start_date");
            endScheduledate = extras.getString("end_date");
            orderScheduleType = extras.getString("order_type");
            parent_id = extras.getString("parent_id");
            //if (orderScheduleType.equals("daily"))
            change_date_formate(startScheduledate,endScheduledate);
            /*else{


            }*/
        }

    }

}
