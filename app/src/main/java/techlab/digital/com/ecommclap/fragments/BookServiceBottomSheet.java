package techlab.digital.com.ecommclap.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.activity.ProductListings;
import techlab.digital.com.ecommclap.activity.ServiceDescriptionActivity;

@SuppressLint("ValidFragment")
public class BookServiceBottomSheet  extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener{
    CardView select_date;
    final Context context;
    TextView mBookingDate;
    Button book_now;
    onDateSelected onDateSelected;
    @SuppressLint("ValidFragment")
    public BookServiceBottomSheet(Context mtx) {
        this.context = mtx;
    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onDateSelected = (BookServiceBottomSheet.onDateSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }











    @SuppressLint("RestrictedApi")
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.service_book_sheet, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        select_date = view.findViewById(R.id.cardView_Date_selecter);
        mBookingDate = view.findViewById(R.id.booking_dateView);
        book_now = view.findViewById(R.id.book_now);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createCustomDialog();
            }
        });

        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBookingDate.getText().toString().matches(""))
                {
                    /*call interface callback*/

                    onDateSelected.selected_date_is(String.valueOf(mBookingDate.getText()));
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getContext(), "Please Select the date first", Toast.LENGTH_SHORT).show();

                }
            }
        });





        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
    }

    int yy, mm, dd;
    public void createCustomDialog(){
        final Calendar calendar = Calendar.getInstance();
        yy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepickerCustom, this, yy, mm, dd);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.setTitle("Select Date");
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();

    }



    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        populateSetDate(i, i1 , i2);

    }

    String myValue, selectedDate="";
    String passingDate ="";
    public void populateSetDate(int year, int month, int day) {
        selectedDate = checkDigit(day)+"-"+checkDigit(month+1)+"-"+year;
        passingDate = year+"-"+checkDigit(month+1)+"-"+checkDigit(day);
        String next = getResources().getString(R.string.sub_booking_date);
        mBookingDate.setVisibility(View.VISIBLE);
        mBookingDate.setText(Html.fromHtml(next) + selectedDate);
        Log.e("Selected date is ",selectedDate);
        mBookingDate.setTextColor(Color.rgb(255, 193, 7));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

    }


    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }



    /*setting interface to get selected date*/


    public interface onDateSelected {
        public void selected_date_is(String date);
    }

}